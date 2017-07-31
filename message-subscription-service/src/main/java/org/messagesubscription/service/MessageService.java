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

@Service
public class MessageService implements IMessageService {

	@Autowired
	MessageRepository messageRepo;

	@Autowired
	MessageTypeRepository messageTypeRepo;

	@Override
	public Message createMessage(Message message) {
		Optional<MessageTypeEntity> existingMessageType = messageTypeRepo.findByType(message.getMessageType().getType());
		Message newMessage = null;
		MessageEntity newMessageEntity = null;
		if (existingMessageType.isPresent()) {
			MessageEntity messageEntity = new MessageEntity();
			BeanUtils.copyProperties(message, messageEntity);
			messageEntity.setMessageType(existingMessageType.get());
			newMessageEntity = messageRepo.saveAndFlush(messageEntity);
		}
		if (newMessageEntity != null) {
			BeanUtils.copyProperties(newMessageEntity, newMessage);
		}
		return newMessage;
	}

	@Override
	public List<Message> findMessages() {
		List<MessageEntity> existingMessages = messageRepo.findAll();
		List<Message> messages = new ArrayList<Message>();
		for (MessageEntity existingMessage : existingMessages) {
			Message message = new Message(new MessageType());
			BeanUtils.copyProperties(existingMessage, message);
			BeanUtils.copyProperties(existingMessage.getMessageType(), message.getMessageType(), "messages");
			messages.add(message);
		}
		return messages;
	}

}
