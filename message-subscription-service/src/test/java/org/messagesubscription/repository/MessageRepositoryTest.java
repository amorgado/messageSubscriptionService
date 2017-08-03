package org.messagesubscription.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
// @Transactional
@AutoConfigureTestDatabase
public class MessageRepositoryTest {

	public static final Logger logger = LoggerFactory.getLogger(SubscriptionRepositoryTest.class);

	@Autowired
	private MessageTypeRepository messageTypeRepo;

	@Autowired
	private MessageRepository messageRepo;

	@Test
	public void findMessagesTest() throws Exception {
		List<MessageEntity> messages = messageRepo.findAll();
		assertThat(messages.get(0).getDescription().equals("Red color is hot."));
		for (MessageEntity messageEntity : messages) {
			System.out.println(messageEntity);
		}
	}

	@Test
	public void updateMessagesTest() throws Exception {
		List<MessageTypeEntity> messageTypes = messageTypeRepo.findAll();
		for (MessageTypeEntity messageTypeEntity : messageTypes) {
			System.out.println("\n\n Before = " + messageTypeEntity + "\n\n");
		}
		List<MessageEntity> messages = messageRepo.findAll();
		for (MessageEntity messageEntity : messages) {
			System.out.println("\n\n Before = " + messageEntity + "\n\n");
		}
		MessageTypeEntity messageType = new MessageTypeEntity((long) 2, MessageTypeEnum.BLUE.name());
		MessageEntity message = new MessageEntity((long) 3, "New green color", messageType);
		messageRepo.saveAndFlush(message);
		List<MessageEntity> messages2 = messageRepo.findAll();
		for (MessageEntity messageEntity : messages2) {
			System.out.println("\n\n After = " + messageEntity + "\n\n");
		}
	}

	// @Test
	// public void getSubscriptionsTest() throws Exception {
	// SubscriptionEntity subscription = messageRepo.getOne((long) 8);
	// assertThat(subscription.getEmail().equals("alexei_morgado@yahoo.com"));
	//
	// }

}
