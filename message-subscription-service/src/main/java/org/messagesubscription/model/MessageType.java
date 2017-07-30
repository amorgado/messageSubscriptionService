package org.messagesubscription.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.messagesubscription.enums.MessageTypeEnum;

public class MessageType {

	private Long id;

	private MessageTypeEnum type;

	@Valid
	@NotNull
	private List<Message> messages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageTypeEnum getType() {
		return type;
	}

	public void setType(MessageTypeEnum type) {
		this.type = type;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
