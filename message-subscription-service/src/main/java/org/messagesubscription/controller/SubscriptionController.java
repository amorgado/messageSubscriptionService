package org.messagesubscription.controller;

import java.util.List;

import javax.validation.Valid;

import org.messagesubscription.model.Response;
import org.messagesubscription.model.Subscription;
import org.messagesubscription.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "Subscription Controller")
@RestController
public class SubscriptionController {

	@Autowired
	ISubscriptionService subscriptionService;

	@RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
	public ResponseEntity<List<Subscription>> findSubscriptions() {
		List<Subscription> subscriptions = subscriptionService.findSubscriptions();
		return new ResponseEntity<List<Subscription>>(subscriptions, HttpStatus.OK);
	}

	@RequestMapping(path = "/subscriptions", method = RequestMethod.POST)
	public ResponseEntity<Response> createSubscription(@Valid @RequestBody Subscription subscription) {
		subscriptionService.createSubscription(subscription);
		Response response = new Response("Subscription created successfully");
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/subscriptions", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateSubscription(@Valid @RequestBody Subscription subscription) {
		subscriptionService.updateSubscription(subscription);
		Response response = new Response("Subscription updated successfully");
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

}
