package org.messagesubscription.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase
public class MessageTypeRepositoryTest {

	@Autowired
	private MessageTypeRepository messageTypeRepo;

	@Autowired
	private MessageRepository messageRepo;

	// Delete cascade works for Message Types deleting Messages and SubscriptionsMessageTypes also if eager fetch removed.
	@Test
	public void deleteCascadeTest() throws Exception {
		List<MessageTypeEntity> messageTypes = messageTypeRepo.findAll();
		for (MessageTypeEntity messageTypeEntity : messageTypes) {
			System.out.println("Before = " + messageTypeEntity);
		}
		List<MessageEntity> messages = messageRepo.findAll();
		for (MessageEntity messageEntity : messages) {
			System.out.println("Before = " + messageEntity);
		}
		messageTypeRepo.deleteById((long) 1);
		messageTypeRepo.flush();
		List<MessageTypeEntity> messageTypes2 = messageTypeRepo.findAll();
		for (MessageTypeEntity messageTypeEntity : messageTypes2) {
			System.out.println("After = " + messageTypeEntity);
		}
		List<MessageEntity> messages2 = messageRepo.findAll();
		for (MessageEntity messageEntity : messages2) {
			System.out.println("After = " + messageEntity);
		}
	}

}
