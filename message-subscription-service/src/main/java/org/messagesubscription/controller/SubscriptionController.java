package org.messagesubscription.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.messagesubscription.model.Subscription;
import org.messagesubscription.service.ISubscriptionService;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;

@Api(value = "Subscription Controller")
@RestController
@RequestMapping(MessageSubscriptionConstants.SUBSRIPTIONS_PATH)
public class SubscriptionController {

	@Autowired
	ISubscriptionService subscriptionService;

	@GetMapping("/{id}")
	public ResponseEntity<Subscription> get(@PathVariable("id") long id) {
		return ResponseEntity.ok(subscriptionService.getSubscription(id));
	}

	@GetMapping
	public ResponseEntity<List<Subscription>> find() {
		return ResponseEntity.ok(subscriptionService.findSubscriptions());
	}

	@PostMapping
	public ResponseEntity<?> add(@Valid @RequestBody Subscription inputSubscription) {
		Subscription newSubscription = subscriptionService.createSubscription(inputSubscription);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSubscription.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Subscription inputSubscription) {
		Subscription updatedSubscription = subscriptionService.updateSubscription(inputSubscription);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(updatedSubscription.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

}
