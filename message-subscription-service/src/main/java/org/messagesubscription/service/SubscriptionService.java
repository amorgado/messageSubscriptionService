package org.messagesubscription.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.stereotype.Service;
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
	@Transactional
	public Subscription createSubscription(Subscription inputSubscription) {
		Subscription newSubscription = null;
		Optional<SubscriptionEntity> existingSubscription = subscriptionRepo.findByEmail(inputSubscription.getEmail());
		if (existingSubscription.isPresent()) {
			throw new RuntimeException("A subscription with email " + inputSubscription.getEmail() + " already exists.");
		} else {
			SubscriptionEntity createdSubscriptionEntity = subscriptionRepo.saveAndFlush(new SubscriptionEntity(inputSubscription.getEmail()));
			List<SubscriptionsMessageTypesEntity> createdSubscriptionsMessageTypes = saveSubscriptionMessageTypes(inputSubscription, createdSubscriptionEntity, false);
			if (createdSubscriptionEntity != null && createdSubscriptionsMessageTypes != null) {
				newSubscription = new Subscription(createdSubscriptionEntity.getId(), createdSubscriptionEntity.getEmail());
			}
		}
		return newSubscription;
	}

	@Override
	@Transactional
	public Subscription updateSubscription(Subscription subscription) {
		if (subscription.getId() == null) {
			throw new RuntimeException("Update can not be executed. Subscription doesn't exist.");
		}
		Optional<SubscriptionEntity> currentSubscriptionEntity = subscriptionRepo.findById(subscription.getId());
		if (!currentSubscriptionEntity.isPresent()) {
			throw new RuntimeException("Update can not be executed. Subscription doesn't exist.");
		} else {
			BeanUtils.copyProperties(subscription, currentSubscriptionEntity.get(), "subscriptionsMessageTypes");
			subscriptionRepo.saveAndFlush(currentSubscriptionEntity.get());
		}
		for (SubscriptionsMessageTypesEntity subMType : currentSubscriptionEntity.get().getSubscriptionsMessageTypes()) {
			subscriptionMessageTypeRepo.deleteById(subMType.getId());
		}

		saveSubscriptionMessageTypes(subscription, currentSubscriptionEntity.get(), true);
		return subscription;
	}

	private List<SubscriptionsMessageTypesEntity> saveSubscriptionMessageTypes(Subscription subscription, SubscriptionEntity currentSubscriptionEntity, boolean updating) {
		List<MessageType> messageTypes = subscription.getMessageTypes();
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes = new ArrayList<SubscriptionsMessageTypesEntity>();
		if (!CollectionUtils.isEmpty(messageTypes)) {
			for (MessageType messageType : messageTypes) {
				Optional<MessageTypeEntity> existingMessageType = messageTypeRepo.findByType(messageType.getType());
				if (existingMessageType.isPresent()) {
					subscriptionsMessageTypes.add(new SubscriptionsMessageTypesEntity(existingMessageType.get(), currentSubscriptionEntity));
				} else {
					throw new RuntimeException("Subscription can not be " + (updating ? "updated" : "created") + ". Message type doesn't exist.");
				}
			}
		}
		return subscriptionMessageTypeRepo.saveAll(subscriptionsMessageTypes);
	}

	@Override
	public Subscription getSubscription(Subscription subscription) {
		Optional<SubscriptionEntity> existingSubscriptionEntity = subscriptionRepo.findByEmail(subscription.getEmail());
		Subscription existingSubscription = new Subscription();
		BeanUtils.copyProperties(existingSubscriptionEntity.get(), existingSubscription);
		return existingSubscription;
	}

	@Override
	public List<Subscription> findSubscriptions() {
		List<SubscriptionEntity> existingSubscriptions = subscriptionRepo.findAll();
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		for (SubscriptionEntity existingSubscription : existingSubscriptions) {
			Subscription subscription = new Subscription();
			copyMessagesFromSubscriptionEntityToSubscription(existingSubscription, subscription);
			subscriptions.add(subscription);
		}
		return subscriptions;
	}

	private void copyMessagesFromSubscriptionEntityToSubscription(SubscriptionEntity subscriptionEntity, Subscription subscription) {
		BeanUtils.copyProperties(subscriptionEntity, subscription);
		List<SubscriptionsMessageTypesEntity> existingSubsMessageTypes = subscriptionEntity.getSubscriptionsMessageTypes();

		List<MessageType> messageTypes = new ArrayList<MessageType>();
		if (!CollectionUtils.isEmpty(existingSubsMessageTypes)) {
			for (SubscriptionsMessageTypesEntity existingSubscriptionsMessageTypes : existingSubsMessageTypes) {
				MessageType messageType = new MessageType();
				BeanUtils.copyProperties(existingSubscriptionsMessageTypes.getMessageType(), messageType, "messages");
				int noOfMsTypesBySubscription = subscriptionMessageTypeRepo.findNumberOfMessageTypesBySubscription(existingSubscriptionsMessageTypes.getMessageType().getId());
				messageType.setNoOfTimesReceivedByASubscription(noOfMsTypesBySubscription);
				messageTypes.add(messageType);
			}
		}
		subscription.setMessageTypes(messageTypes);
	}

}
