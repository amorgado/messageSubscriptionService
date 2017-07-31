package org.messagesubscription.service;

import java.util.List;

import org.messagesubscription.model.Message;

public interface IMessageService {

	public Message createMessage(Message message);

	public List<Message> findMessages();

}
