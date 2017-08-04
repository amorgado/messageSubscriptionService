package org.messagesubscription.repository;

import org.junit.runner.RunWith;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase
public abstract class BaseRepositoryTest {

	protected final XLogger logger = XLoggerFactory.getXLogger(this.getClass());

	@Autowired
	protected MessageTypeRepository messageTypeRepo;

	@Autowired
	protected MessageRepository messageRepo;

	@Autowired
	protected SubscriptionRepository subscriptionRepo;

	@Autowired
	protected SubscriptionMessageTypeRepository subscriptionMessageTypeRepository;

}
