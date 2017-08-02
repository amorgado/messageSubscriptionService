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
import org.messagesubscription.repository.SubscriptionMessageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class MessageService implements IMessageService {

	@Autowired
	MessageRepository messageRepo;

	@Autowired
	MessageTypeRepository messageTypeRepo;

	@Autowired
	private SubscriptionMessageTypeRepository subscriptionMessageTypeRepo;

	@Override
	public Message getMessage(Long id) {
		return populateModelFromEntity(messageRepo.getOne(id));
	}

	@Override
	@Transactional
	public Message createMessage(Message message) {
		Message newMessage = null;
		if (message == null || message.getMessageType() == null) {
			throw new RuntimeException("Message could not be created because message or message type are empty.");
		}
		Optional<MessageTypeEntity> existingMessageType = messageTypeRepo.findByType(message.getMessageType().getType());
		if (!existingMessageType.isPresent()) {
			throw new RuntimeException("Message could not be created because message type " + message.getMessageType().getType() + " doesn't exist.");
		} else {
			// Saving the message with the message Type id.
			MessageEntity createdMessageEntity = messageRepo.saveAndFlush(new MessageEntity(message.getDescription(), existingMessageType.get()));
			if (createdMessageEntity != null) {
				newMessage = new Message(createdMessageEntity.getId());
			} else {
				throw new RuntimeException("Message could not be created due to an internal error.");
			}
		}
		return newMessage;
	}

	@Override
	public List<Message> findMessages() {
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
		return messages;
	}

	private Message populateModelFromEntity(MessageEntity messageEntity) {
		int noOfMsTypesBySubscription = subscriptionMessageTypeRepo.findNumberOfMessageTypesBySubscription(messageEntity.getMessageType().getId());
		return new Message(messageEntity.getId(), messageEntity.getDescription(), new MessageType(messageEntity.getMessageType().getType(), noOfMsTypesBySubscription));
	}

}
