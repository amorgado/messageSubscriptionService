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
public class SubscriptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subscription")
	private List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes;

	public SubscriptionEntity() {
		super();
	}

	public SubscriptionEntity(String email) {
		super();
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SubscriptionsMessageTypesEntity> getSubscriptionsMessageTypes() {
		return subscriptionsMessageTypes;
	}

	public void setSubscriptionsMessageTypes(List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypes) {
		this.subscriptionsMessageTypes = subscriptionsMessageTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (subscriptionsMessageTypes == null ? 0 : subscriptionsMessageTypes.hashCode());
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
		SubscriptionEntity other = (SubscriptionEntity) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (subscriptionsMessageTypes == null) {
			if (other.subscriptionsMessageTypes != null) {
				return false;
			}
		} else if (!subscriptionsMessageTypes.equals(other.subscriptionsMessageTypes)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SubscriptionEntity [id=" + id + ", email=" + email + ", subscriptionsMessageTypes=" + subscriptionsMessageTypes + "]";
	}

}
