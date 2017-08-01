package org.messagesubscription.model;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class MessageType {

	private Long id;

	@NotEmpty
	@Pattern(regexp = "RED|BLUE|GREEN|YELLOW")
	private String type;

	private List<Message> messages;

	private int noOfTimesReceivedByASubscription;

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

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getNoOfTimesReceivedByASubscription() {
		return noOfTimesReceivedByASubscription;
	}

	public void setNoOfTimesReceivedByASubscription(int noOfTimesReceivedByASubscription) {
		this.noOfTimesReceivedByASubscription = noOfTimesReceivedByASubscription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (messages == null ? 0 : messages.hashCode());
		result = prime * result + noOfTimesReceivedByASubscription;
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
		MessageType other = (MessageType) obj;
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
		if (noOfTimesReceivedByASubscription != other.noOfTimesReceivedByASubscription) {
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
