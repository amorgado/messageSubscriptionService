package org.messagesubscription.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.model.Message;
import org.messagesubscription.model.MessageType;
import org.messagesubscription.repository.MessageRepository;
import org.messagesubscription.repository.MessageTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService implements IMessageService {

	@Autowired
	MessageRepository messageRepo;

	@Autowired
	MessageTypeRepository messageTypeRepo;

	@Override
	@Transactional
	public Message createMessage(Message message) {
		Optional<MessageTypeEntity> existingMessageType = messageTypeRepo.findByType(message.getMessageType().getType());
		MessageEntity newMessageEntity = null;
		if (!existingMessageType.isPresent()) {
			throw new RuntimeException("Message can not be created because message type " + message.getMessageType().getType() + " doesn't exist.");
		} else {
			newMessageEntity = messageRepo.saveAndFlush(new MessageEntity(message.getDescription(), existingMessageType.get()));
		}
		return populateMessageFromMessageEntity(newMessageEntity);
	}

	@Override
	public List<Message> findMessages() {
		List<MessageEntity> existingMessages = messageRepo.findAll();
		List<Message> messages = new ArrayList<Message>();
		for (MessageEntity existingMessage : existingMessages) {
			Message message = populateMessageFromMessageEntity(existingMessage);
			messages.add(message);
		}
		return messages;
	}

	private Message populateMessageFromMessageEntity(MessageEntity existingMessage) {
		Message message = new Message(new MessageType());
		BeanUtils.copyProperties(existingMessage, message);
		BeanUtils.copyProperties(existingMessage.getMessageType(), message.getMessageType(), "messages");
		return message;
	}

}
