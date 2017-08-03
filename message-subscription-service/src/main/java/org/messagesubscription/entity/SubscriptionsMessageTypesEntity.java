package org.messagesubscription.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "message_type_id", "subscription_id" }) })
public class SubscriptionsMessageTypesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	MessageTypeEntity messageType;

	@ManyToOne
	SubscriptionEntity subscription;

	public SubscriptionsMessageTypesEntity() {
		super();
	}

	public SubscriptionsMessageTypesEntity(MessageTypeEntity messageType) {
		super();
		this.messageType = messageType;
	}

	public SubscriptionsMessageTypesEntity(MessageTypeEntity messageType, SubscriptionEntity subscription) {
		super();
		this.messageType = messageType;
		this.subscription = subscription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageTypeEntity getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageTypeEntity messageType) {
		this.messageType = messageType;
	}

	public SubscriptionEntity getSubscription() {
		return subscription;
	}

	public void setSubscription(SubscriptionEntity subscription) {
		this.subscription = subscription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (messageType == null ? 0 : messageType.hashCode());
		result = prime * result + (subscription == null ? 0 : subscription.hashCode());
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
		SubscriptionsMessageTypesEntity other = (SubscriptionsMessageTypesEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (messageType == null) {
			if (other.messageType != null) {
				return false;
			}
		} else if (!messageType.equals(other.messageType)) {
			return false;
		}
		if (subscription == null) {
			if (other.subscription != null) {
				return false;
			}
		} else if (!subscription.equals(other.subscription)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SubscriptionsMessageTypesEntity [id=" + id + ", messageType=" + messageType + "]";
	}

}
