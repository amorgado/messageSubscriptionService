package org.messagesubscription.service;

import java.util.ArrayList;
import java.util.List;

import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.model.Message;
import org.messagesubscription.model.MessageType;
import org.messagesubscription.repository.MessageRepository;
import org.messagesubscription.repository.MessageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class MessageService implements IMessageService {
	
	private final XLogger logger = XLoggerFactory.getXLogger(this.getClass());

	@Autowired
	MessageRepository messageRepo;

	@Autowired
	MessageTypeRepository messageTypeRepo;

	@Override
	public Message getMessage(Long id) {
		return populateModelFromEntity(messageRepo.getOne(id));
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Message createMessage(Message inputMessage) {
		logger.entry(inputMessage);
		Message newMessage = null;
		if (inputMessage == null || inputMessage.getMessageType() == null) {
			throw new RuntimeException("Message could not be created because message or message type are empty.");
		}
		MessageTypeEntity existingMessageType = null;
		if (inputMessage.getMessageType().getId() != null) {
			existingMessageType = messageTypeRepo.getOne(inputMessage.getMessageType().getId());
		} else {
			throw new RuntimeException("Message could not be created because message type id wasn't provided.");
		}
		if (existingMessageType == null || existingMessageType.getId() == null) {
			throw new RuntimeException("Message could not be created because message type " + inputMessage.getMessageType().getType() + " doesn't exist.");
		} else {
			// Saving the message with the message Type id.
			MessageEntity createdMessageEntity = null;
			try {
				createdMessageEntity = messageRepo.saveAndFlush(new MessageEntity(inputMessage.getDescription(), existingMessageType));
			} catch (DataIntegrityViolationException e) {
				throw new RuntimeException("Message could not be created because this combination of description and message type already exists and it should be unique or message Type doesn't exist.");
			}
			if (createdMessageEntity != null) {
				newMessage = new Message(createdMessageEntity.getId());
			} else {
				throw new RuntimeException("Message could not be created due to an internal error.");
			}
		}
		logger.exit(newMessage);
		return newMessage;
	}

	@Override
	public List<Message> findMessages() {
		logger.entry();
		List<Message> messages = null;
		List<MessageEntity> existingMessages = messageRepo.findAll();
		if (!CollectionUtils.isEmpty(existingMessages)) {
			messages = new ArrayList<Message>();
			for (MessageEntity existingMessage : existingMessages) {
				messages.add(populateModelFromEntity(existingMessage));
			}
		} else {
			throw new RuntimeException("Not messages found.");
		}
		logger.exit(messages);
		return messages;
	}

	private Message populateModelFromEntity(MessageEntity messageEntity) {
		return new Message(messageEntity.getId(), messageEntity.getDescription(), new MessageType(messageEntity.getMessageType().getType()));
	}

}
