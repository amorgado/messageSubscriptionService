package org.messagesubscription.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.entity.SubscriptionEntity;
import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.messagesubscription.model.MessageType;
import org.messagesubscription.model.Subscription;
import org.messagesubscription.repository.MessageTypeRepository;
import org.messagesubscription.repository.SubscriptionMessageTypeRepository;
import org.messagesubscription.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class SubscriptionService implements ISubscriptionService {

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private MessageTypeRepository messageTypeRepo;

	@Autowired
	private SubscriptionMessageTypeRepository subscriptionMessageTypeRepo;

	@Override
	public Subscription getSubscription(Long id) {
		return populateModelFromEntity(subscriptionRepo.getOne(id));
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Subscription createSubscription(Subscription inputSubscription) {
		Subscription newSubscription = null;
		Optional<SubscriptionEntity> existingSubscription = subscriptionRepo.findByEmail(inputSubscription.getEmail());
		if (existingSubscription.isPresent()) {
			throw new RuntimeException("A subscription with email " + inputSubscription.getEmail() + " already exists.");
		} else {
			// Save subscription entity.
			SubscriptionEntity createdSubscriptionEntity = subscriptionRepo.saveAndFlush(new SubscriptionEntity(inputSubscription.getEmail()));
			if (createdSubscriptionEntity != null) {
				// Save messageTypes in subscription.
				List<SubscriptionsMessageTypesEntity> createdSubscriptionsMessageTypes = saveSubscriptionMessageTypes(inputSubscription, createdSubscriptionEntity, false);
				if (createdSubscriptionsMessageTypes != null) {
					newSubscription = new Subscription(createdSubscriptionEntity.getId());
				} else {
					throw new RuntimeException("Subscription could not be created due to an internal error.");
				}
			}
		}
		return newSubscription;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Subscription updateSubscription(Subscription inputSubscription) {
		Subscription updatedSubscription = null;
		if (inputSubscription == null || inputSubscription.getId() == null) {
			throw new RuntimeException("Update could not be executed. Subscription doesn't exist.");
		}
		Optional<SubscriptionEntity> existingSubscriptionEntity = subscriptionRepo.findById(inputSubscription.getId());
		if (!existingSubscriptionEntity.isPresent()) {
			throw new RuntimeException("Update could not be executed. Subscription doesn't exist.");
		} else {
			// subscriptionRepo.deleteById(existingSubscriptionEntity.get().getId());
			// updatedSubscription = this.createSubscription(inputSubscription);
			BeanUtils.copyProperties(inputSubscription, existingSubscriptionEntity.get(), "subscriptionsMessageTypes");
			// Updating subscription entity.
			SubscriptionEntity updatedSubscriptionEntity = subscriptionRepo.saveAndFlush(existingSubscriptionEntity.get());
			if (updatedSubscriptionEntity != null) {
				// Deleting the message types related to this subscription in the table containing the many to many relationship.
				subscriptionMessageTypeRepo.deleteBySubscriptionId(updatedSubscriptionEntity.getId());
				subscriptionMessageTypeRepo.flush();
				if (!CollectionUtils.isEmpty(inputSubscription.getMessageTypes())) {
					// Creating new subscriptionsMessageTypes entities based on the message types passed in the input.
					List<SubscriptionsMessageTypesEntity> createdSubscriptionsMessageTypes = saveSubscriptionMessageTypes(inputSubscription, existingSubscriptionEntity.get(), true);
					if (createdSubscriptionsMessageTypes != null) {
						updatedSubscription = new Subscription(updatedSubscriptionEntity.getId());
					} else {
						throw new RuntimeException("Subscription could not be created due to an internal error.");
					}
				} else {
					throw new RuntimeException("Update could not be executed. At least one message type is required.");
				}
			}
		}
		return updatedSubscription;
	}

	private List<SubscriptionsMessageTypesEntity> saveSubscriptionMessageTypes(Subscription inputSubscription, SubscriptionEntity subscriptionEntity, boolean updating) {
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes = new ArrayList<SubscriptionsMessageTypesEntity>();
		if (!CollectionUtils.isEmpty(inputSubscription.getMessageTypes())) {
			for (MessageType messageType : inputSubscription.getMessageTypes()) {
				MessageTypeEntity existingMessageType = null;
				if (messageType.getId() != null) {
					existingMessageType = messageTypeRepo.getOne(messageType.getId());
				} else if (StringUtils.isNoneBlank(messageType.getType())) {
					existingMessageType = messageTypeRepo.findByType(messageType.getType());
				} else {
					throw new RuntimeException("Subscription can not be " + (updating ? "updated" : "created") + ". Message type id or type weren't provided.");
				}
				if (existingMessageType != null) {
					subscriptionsMessageTypes.add(new SubscriptionsMessageTypesEntity(existingMessageType, subscriptionEntity));
				} else {
					throw new RuntimeException("Subscription can not be " + (updating ? "updated" : "created") + ". Message type '" + messageType.getType() + "' doesn't exist.");
				}
			}
		}
		List<SubscriptionsMessageTypesEntity> createdSubMsTypes = null;
		try {
			createdSubMsTypes = subscriptionMessageTypeRepo.saveAll(subscriptionsMessageTypes);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Subscription could not be created because at least one of the combinations of email and message type you are trying to update already exists.");
		}
		return createdSubMsTypes;
	}

	@Override
	public List<Subscription> findSubscriptions() {
		List<Subscription> subscriptions = null;
		List<SubscriptionEntity> existingSubscriptions = subscriptionRepo.findAll();
		if (!CollectionUtils.isEmpty(existingSubscriptions)) {
			subscriptions = new ArrayList<Subscription>();
			for (SubscriptionEntity existingSubscription : existingSubscriptions) {
				subscriptions.add(populateModelFromEntity(existingSubscription));
			}
		} else {
			throw new RuntimeException("Not subscriptions found.");
		}
		return subscriptions;
	}

	private Subscription populateModelFromEntity(SubscriptionEntity subscriptionEntity) {
		Subscription newSubscription = new Subscription(subscriptionEntity.getId(), subscriptionEntity.getEmail());
		if (!CollectionUtils.isEmpty(subscriptionEntity.getSubscriptionsMessageTypes())) {
			List<MessageType> messageTypes = new ArrayList<MessageType>();
			for (SubscriptionsMessageTypesEntity existingSubMsTypes : subscriptionEntity.getSubscriptionsMessageTypes()) {
				int noOfMsTypesBySubscription = subscriptionMessageTypeRepo.findNumberOfMessageTypesBySubscription(existingSubMsTypes.getMessageType().getId());
				messageTypes.add(new MessageType(existingSubMsTypes.getMessageType().getType(), noOfMsTypesBySubscription));
			}
			newSubscription.setMessageTypes(messageTypes);
		}
		return newSubscription;
	}

}
