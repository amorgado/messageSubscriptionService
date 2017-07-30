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
import org.messagesubscription.repository.SubscriptionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements ISubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private MessageTypeRepository messageTypeRepo;

	@Override
	public Subscription createSubscription(Subscription subscription) {
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		BeanUtils.copyProperties(subscription, subscriptionEntity);
		List<MessageType> messageTypes = subscription.getMessageTypes();
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes = new ArrayList<SubscriptionsMessageTypesEntity>();
		for (MessageType messageType : messageTypes) {
			Optional<MessageTypeEntity> existingMessageType = messageTypeRepo.findByType(messageType.getType().name());
			if (existingMessageType.isPresent()) {
				SubscriptionsMessageTypesEntity subMessageTypeEntity = new SubscriptionsMessageTypesEntity();
				subMessageTypeEntity.setMessageType(existingMessageType.get());
				subMessageTypeEntity.setSubscription(subscriptionEntity);
				subscriptionsMessageTypes.add(subMessageTypeEntity);
			} else {
				throw new RuntimeException("Subscription can not be created. Message type doesn't exist.");
			}
		}
		subscriptionEntity.setSubscriptionsMessageTypes(subscriptionsMessageTypes);
		subscriptionRepo.saveAndFlush(subscriptionEntity);
		BeanUtils.copyProperties(subscriptionEntity, subscription);
		return subscription;
	}

	@Override
	public Subscription updateSubscription(Subscription subscription) {
		Optional<SubscriptionEntity> currentSubscriptionEntity = subscriptionRepo.findById(subscription.getId());
		if (!currentSubscriptionEntity.isPresent()) {
			throw new RuntimeException("Update can not be executed. Subscription doesn't exist.");
		}
		List<MessageType> messageTypes = subscription.getMessageTypes();
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes = new ArrayList<SubscriptionsMessageTypesEntity>();
		for (MessageType messageType : messageTypes) {
			Optional<MessageTypeEntity> existingMessageType = messageTypeRepo.findByType(messageType.getType().name());
			if (existingMessageType.isPresent()) {
				SubscriptionsMessageTypesEntity subMessageTypeEntity = new SubscriptionsMessageTypesEntity();
				subMessageTypeEntity.setMessageType(existingMessageType.get());
				subMessageTypeEntity.setSubscription(currentSubscriptionEntity.get());
				subscriptionsMessageTypes.add(subMessageTypeEntity);
			} else {
				throw new RuntimeException("Update can not be executed. Message type doesn't exist.");
			}
		}
		currentSubscriptionEntity.get().setSubscriptionsMessageTypes(subscriptionsMessageTypes);
		subscriptionRepo.saveAndFlush(currentSubscriptionEntity.get());
		BeanUtils.copyProperties(currentSubscriptionEntity, subscription);
		return subscription;
	}

	@Override
	public Subscription getSubscription(Subscription subscription) {
		Optional<SubscriptionEntity> existingSubscriptionEntity = subscriptionRepo.findByEmail(subscription.getEmail());
		Subscription existingSubscription = new Subscription();
		BeanUtils.copyProperties(existingSubscriptionEntity.get(), existingSubscription);
		return existingSubscription;
	}

	public List<Subscription> findSubscriptions() {
		List<SubscriptionEntity> existingSubscriptions = subscriptionRepo.findAll();
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		for (SubscriptionEntity subscriptionEntity : existingSubscriptions) {
			Subscription subscription = new Subscription();
			BeanUtils.copyProperties(subscriptionEntity, subscription);
			subscriptions.add(subscription);
		}
		return subscriptions;
	}

}
