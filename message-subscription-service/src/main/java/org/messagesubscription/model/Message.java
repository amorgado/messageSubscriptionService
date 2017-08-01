package org.messagesubscription.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class Message {

	private Long id;

	@NotEmpty
	private String description;

	@Valid
	private MessageType messageType;

	public Message() {
		super();
	}

	public Message(MessageType messageType) {
		super();
		this.messageType = messageType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (messageType == null ? 0 : messageType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Message other = (Message) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (messageType != other.messageType) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", description=" + description + ", messageType=" + messageType + "]";
	}

}
