package org.messagesubscription.repository;

import java.util.List;

import org.junit.Test;
import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;

public class MessageTypeRepositoryTest extends BaseRepositoryTest {

	// Delete cascade works for Message Types deleting Messages and SubscriptionsMessageTypes also if eager fetch removed.
	@Test
	public void deleteCascadeTest() throws Exception {
		List<MessageTypeEntity> messageTypes = messageTypeRepo.findAll();
		for (MessageTypeEntity messageTypeEntity : messageTypes) {
			logger.debug("Before = " + messageTypeEntity);
		}
		List<MessageEntity> messages = messageRepo.findAll();
		for (MessageEntity messageEntity : messages) {
			logger.debug("Before = " + messageEntity);
		}
		messageTypeRepo.deleteById((long) 1);
		messageTypeRepo.flush();
		List<MessageTypeEntity> messageTypes2 = messageTypeRepo.findAll();
		for (MessageTypeEntity messageTypeEntity : messageTypes2) {
			logger.debug("After = " + messageTypeEntity);
		}
		List<MessageEntity> messages2 = messageRepo.findAll();
		for (MessageEntity messageEntity : messages2) {
			logger.debug("After = " + messageEntity);
		}
	}

}
