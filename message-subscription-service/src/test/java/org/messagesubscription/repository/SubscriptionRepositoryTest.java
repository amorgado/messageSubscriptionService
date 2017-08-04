package org.messagesubscription.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.messagesubscription.entity.SubscriptionEntity;
import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionRepositoryTest extends BaseRepositoryTest {

	public static final Logger logger = LoggerFactory.getLogger(SubscriptionRepositoryTest.class);

	@Test
	public void findSubscriptionsTest() throws Exception {
		List<SubscriptionEntity> subscriptions = subscriptionRepo.findAll();
		assertThat(subscriptions.get(0).getEmail().equals(MessageSubscriptionConstants.MESSAGE));
		for (SubscriptionEntity subscriptionEntity : subscriptions) {
			logger.debug(subscriptionEntity.toString());
		}
	}

	@Test
	public void getSubscriptionsTest() throws Exception {
		SubscriptionEntity subscription = subscriptionRepo.getOne((long) 8);
		assertThat(subscription.getEmail().equals(MessageSubscriptionConstants.MESSAGE));

	}

	// It works
	@Test
	public void deleteCascadeFromSubscriptionToSubsMessageTypesTest() throws Exception {
		List<SubscriptionEntity> subscriptionsTypes = subscriptionRepo.findAll();
		for (SubscriptionEntity subscriptionEntity : subscriptionsTypes) {
			logger.debug("\n\nBefore: " + subscriptionEntity + "\n\n");
		}
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes = subscriptionMessageTypeRepository.findAll();
		for (SubscriptionsMessageTypesEntity subscriptionMessageTypeEntity : subscriptionsMessageTypes) {
			logger.debug("\n\nBefore: " + subscriptionMessageTypeEntity + "\n\n");
		}
		subscriptionRepo.deleteById((long) 1);
		List<SubscriptionEntity> subscriptionsTypes2 = subscriptionRepo.findAll();
		for (SubscriptionEntity subscriptionEntity : subscriptionsTypes2) {
			logger.debug("\n\nAfter:  " + subscriptionEntity + "\n\n");
		}
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes2 = subscriptionMessageTypeRepository.findAll();
		for (SubscriptionsMessageTypesEntity subscriptionMessageTypeEntity : subscriptionsMessageTypes2) {
			logger.debug("\n\nAfter: " + subscriptionMessageTypeEntity + "\n\n");
		}
	}

	@Test
	public void updateRepositoryTest() throws Exception {
		List<SubscriptionEntity> subscriptionsTypes = subscriptionRepo.findAll();
		for (SubscriptionEntity subscriptionEntity : subscriptionsTypes) {
			logger.debug("\n\n" + subscriptionEntity + "\n\n");
		}
	}

}
