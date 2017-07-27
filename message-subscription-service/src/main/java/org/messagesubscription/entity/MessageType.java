package org.messagesubscription.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MessageType {

	@Id
	@GeneratedValue
	private Long id;

	private String messageType;

}
