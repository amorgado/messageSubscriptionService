package org.messagesubscription.model;

import java.util.List;

public class MessageType {

	private Long id;

	private String type;

	private List<Message> messages;

	private Integer noOfTimesReceivedByASubscription;

	public MessageType() {
		super();
	}

	public MessageType(Long id) {
		super();
		this.id = id;
	}

	public MessageType(String type) {
		super();
		this.type = type;
	}

	public MessageType(String type, Integer noOfTimesReceivedByASubscription) {
		super();
		this.type = type;
		this.noOfTimesReceivedByASubscription = noOfTimesReceivedByASubscription;
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

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Integer getNoOfTimesReceivedByASubscription() {
		return noOfTimesReceivedByASubscription;
	}

	public void setNoOfTimesReceivedByASubscription(Integer noOfTimesReceivedByASubscription) {
		this.noOfTimesReceivedByASubscription = noOfTimesReceivedByASubscription;
	}
}
