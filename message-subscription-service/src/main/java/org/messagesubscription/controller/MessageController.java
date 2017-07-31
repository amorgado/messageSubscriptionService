package org.messagesubscription.controller;

import java.util.List;

import javax.validation.Valid;

import org.messagesubscription.model.Message;
import org.messagesubscription.model.Response;
import org.messagesubscription.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "Message Controller")
@RestController
public class MessageController {

	@Autowired
	IMessageService messageService;

	@RequestMapping(path = "/messages", method = RequestMethod.GET)
	public ResponseEntity<List<Message>> findMessage() {
		List<Message> messages = messageService.findMessages();
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}

	@RequestMapping(path = "/messages", method = RequestMethod.POST)
	public ResponseEntity<Response> createMessage(@Valid @RequestBody Message message) {
		messageService.createMessage(message);
		Response response = new Response("Message created successfully");
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

}
