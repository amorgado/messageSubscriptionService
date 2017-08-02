package org.messagesubscription.service;

import java.util.List;

import org.messagesubscription.model.Subscription;

public interface ISubscriptionService {

	public Subscription getSubscription(Long id);

	public List<Subscription> findSubscriptions();

	public Subscription createSubscription(Subscription subscription);

	public Subscription updateSubscription(Subscription subscription);

}
