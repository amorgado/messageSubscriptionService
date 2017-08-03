package org.messagesubscription.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.messagesubscription.entity.SubscriptionEntity;
import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase
public class SubscriptionRepositoryTest {

	public static final Logger logger = LoggerFactory.getLogger(SubscriptionRepositoryTest.class);

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private SubscriptionMessageTypeRepository subscriptionMessageTypeRepository;

	@Test
	public void findSubscriptionsTest() throws Exception {
		List<SubscriptionEntity> subscriptions = subscriptionRepo.findAll();
		assertThat(subscriptions.get(0).getEmail().equals("alexei_morgado@yahoo.com"));
		for (SubscriptionEntity subscriptionEntity : subscriptions) {
			System.out.println(subscriptionEntity);
		}
	}

	@Test
	public void getSubscriptionsTest() throws Exception {
		SubscriptionEntity subscription = subscriptionRepo.getOne((long) 8);
		assertThat(subscription.getEmail().equals("alexei_morgado@yahoo.com"));

	}

	// It works
	@Test
	public void deleteCascadeFromSubscriptionToSubsMessageTypesTest() throws Exception {
		List<SubscriptionEntity> subscriptionsTypes = subscriptionRepo.findAll();
		for (SubscriptionEntity subscriptionEntity : subscriptionsTypes) {
			System.out.println("\n\nBefore: " + subscriptionEntity + "\n\n");
		}
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes = subscriptionMessageTypeRepository.findAll();
		for (SubscriptionsMessageTypesEntity subscriptionMessageTypeEntity : subscriptionsMessageTypes) {
			System.out.println("\n\nBefore: " + subscriptionMessageTypeEntity + "\n\n");
		}
		subscriptionRepo.deleteById((long) 1);
		List<SubscriptionEntity> subscriptionsTypes2 = subscriptionRepo.findAll();
		for (SubscriptionEntity subscriptionEntity : subscriptionsTypes2) {
			System.out.println("\n\nAfter:  " + subscriptionEntity + "\n\n");
		}
		List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes2 = subscriptionMessageTypeRepository.findAll();
		for (SubscriptionsMessageTypesEntity subscriptionMessageTypeEntity : subscriptionsMessageTypes2) {
			System.out.println("\n\nAfter: " + subscriptionMessageTypeEntity + "\n\n");
		}
	}

	@Test
	public void updateRepositoryTest() throws Exception {
		List<SubscriptionEntity> subscriptionsTypes = subscriptionRepo.findAll();
		for (SubscriptionEntity subscriptionEntity : subscriptionsTypes) {
			System.out.println("\n\n" + subscriptionEntity + "\n\n");
		}
	}

}
