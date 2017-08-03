package org.messagesubscription.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.enums.MessageTypeEnum;
import org.messagesubscription.service.XLogger;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

public class MessageRepositoryTest extends BaseRepositoryTest{

	@Test
	public void findMessagesTest() throws Exception {
		List<MessageEntity> messages = messageRepo.findAll();
		assertThat(messages.get(0).getDescription().equals(MessageSubscriptionConstants.MESSAGE));
		for (MessageEntity messageEntity : messages) {
			logger.debug(messageEntity);
		}
	}

	@Test
	public void updateMessagesTest() throws Exception {
		List<MessageTypeEntity> messageTypes = messageTypeRepo.findAll();
		for (MessageTypeEntity messageTypeEntity : messageTypes) {
			logger.debug("\n\n Before = " + messageTypeEntity + "\n\n");
		}
		List<MessageEntity> messages = messageRepo.findAll();
		for (MessageEntity messageEntity : messages) {
			logger.debug("\n\n Before = " + messageEntity + "\n\n");
		}
		MessageTypeEntity messageType = new MessageTypeEntity((long) 2, MessageTypeEnum.BLUE.name());
		MessageEntity message = new MessageEntity((long) 3, "New green color", messageType);
		messageRepo.saveAndFlush(message);
		assertThat(messages.get(1).getDescription().equals("New green color"));
		List<MessageEntity> messages2 = messageRepo.findAll();
		for (MessageEntity messageEntity : messages2) {
			logger.debug("\n\n After = " + messageEntity + "\n\n");
		}
	}

}
