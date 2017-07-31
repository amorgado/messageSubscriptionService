package org.messagesubscription.service;

import java.util.List;

import org.messagesubscription.model.Subscription;

public interface ISubscriptionService {

	public Subscription createSubscription(Subscription subscritpion);

	public Subscription getSubscription(Subscription subscription);

	public Subscription updateSubscription(Subscription subscription);

	public List<Subscription> findSubscriptions();

}
