package org.messagesubscription.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.messagesubscription.model.Message;
import org.messagesubscription.model.Response;
import org.messagesubscription.service.IMessageService;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//@Api(value = "Message Controller")
@RestController
@RequestMapping(MessageSubscriptionConstants.MESSAGES_PATH)
public class MessageController {

	@Autowired
	IMessageService messageService;

	@GetMapping("/{id}")
	public ResponseEntity<Message> get(@PathVariable long id) {
		return ResponseEntity.ok(messageService.getMessage(id));
	}

	@GetMapping
	public ResponseEntity<List<Message>> findMessage() {
		List<Message> messages = messageService.findMessages();
		return ResponseEntity.ok(messages);
	}

	@PostMapping
	public ResponseEntity<Response> createMessage(@Valid @RequestBody Message message) {
		Message newMessage = messageService.createMessage(message);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newMessage.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

}
