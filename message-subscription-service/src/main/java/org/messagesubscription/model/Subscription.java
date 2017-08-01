package org.messagesubscription.model;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Subscription {

	private Long id;

	@Email
	@NotEmpty
	private String email;

	@Valid
	@NotEmpty
	private List<MessageType> messageTypes;

	public Subscription() {
		super();
	}

	public Subscription(Long id, String email) {
		super();
		this.id = id;
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

	public List<MessageType> getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(List<MessageType> messageTypes) {
		this.messageTypes = messageTypes;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", email=" + email + ", messageTypes=" + messageTypes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (messageTypes == null ? 0 : messageTypes.hashCode());
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
		Subscription other = (Subscription) obj;
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
		if (messageTypes == null) {
			if (other.messageTypes != null) {
				return false;
			}
		} else if (!messageTypes.equals(other.messageTypes)) {
			return false;
		}
		return true;
	}

}
