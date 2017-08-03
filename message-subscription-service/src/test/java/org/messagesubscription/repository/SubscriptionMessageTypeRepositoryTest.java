package org.messagesubscription.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase
public class SubscriptionMessageTypeRepositoryTest {

	@Autowired
	private SubscriptionMessageTypeRepository subscriptionMessageTypeRepository;

	// Not cascade (doesn't have to be)
	@Test
	public void deleteTest() throws Exception {
		List<SubscriptionsMessageTypesEntity> subMsTyeps = subscriptionMessageTypeRepository.findAll();
		for (SubscriptionsMessageTypesEntity subscriptionsMessageTypesEntity : subMsTyeps) {
			System.out.println("Before: " + subscriptionsMessageTypesEntity);
		}
		subscriptionMessageTypeRepository.deleteById((long) 1);
		List<SubscriptionsMessageTypesEntity> subMsTyeps2 = subscriptionMessageTypeRepository.findAll();
		System.out.println("Checking..");
		for (SubscriptionsMessageTypesEntity subscriptionsMessageTypesEntity : subMsTyeps2) {
			System.out.println("After: " + subscriptionsMessageTypesEntity);
		}
	}

}