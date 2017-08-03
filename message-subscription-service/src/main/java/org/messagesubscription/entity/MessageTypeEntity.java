package org.messagesubscription.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MessageTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String type;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "messageType")
	private List<SubscriptionsMessageTypesEntity> subscriptionMessageTypes;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "messageType")
	private List<MessageEntity> messages;

	public MessageTypeEntity() {
		super();
	}

	public MessageTypeEntity(String type) {
		super();
		this.type = type;
	}

	public MessageTypeEntity(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public MessageTypeEntity(Long id, String type, List<MessageEntity> messages) {
		super();
		this.id = id;
		this.type = type;
		this.messages = messages;
	}

	public MessageTypeEntity(Long id, String type, List<SubscriptionsMessageTypesEntity> subscriptionMessageTypes, List<MessageEntity> messages) {
		super();
		this.id = id;
		this.type = type;
		this.subscriptionMessageTypes = subscriptionMessageTypes;
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SubscriptionsMessageTypesEntity> getSubscriptionMessageTypes() {
		return subscriptionMessageTypes;
	}

	public void setSubscriptionMessageTypes(List<SubscriptionsMessageTypesEntity> subscriptionMessageTypes) {
		this.subscriptionMessageTypes = subscriptionMessageTypes;
	}

	public List<MessageEntity> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageEntity> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "MessageTypeEntity [id=" + id + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (messages == null ? 0 : messages.hashCode());
		result = prime * result + (subscriptionMessageTypes == null ? 0 : subscriptionMessageTypes.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
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
		MessageTypeEntity other = (MessageTypeEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (messages == null) {
			if (other.messages != null) {
				return false;
			}
		} else if (!messages.equals(other.messages)) {
			return false;
		}
		if (subscriptionMessageTypes == null) {
			if (other.subscriptionMessageTypes != null) {
				return false;
			}
		} else if (!subscriptionMessageTypes.equals(other.subscriptionMessageTypes)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

}
