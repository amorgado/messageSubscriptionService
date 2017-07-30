package org.messagesubscription.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubscriptionsMessageTypesEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	MessageTypeEntity messageType;
	@ManyToOne
	SubscriptionEntity subscription;

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
		return "MessageTypeSubscription [id=" + id + ", messageType=" + messageType + ", subscription=" + subscription
				+ "]";
	}

}
