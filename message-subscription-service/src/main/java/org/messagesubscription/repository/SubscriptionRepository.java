package org.messagesubscription.repository;

import java.util.List;
import java.util.Optional;

import org.messagesubscription.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	public Optional<SubscriptionEntity> findByEmail(@Param("email") String email);

	@Query("Select sub.id, sub.email, subsMTypes.messageType from SubscriptionEntity sub LEFT JOIN SubscriptionsMessageTypesEntity subsMTypes ON sub.id = subsMTypes.subscription.id")
	public List<SubscriptionEntity> findBySubscriptionsMessageTypes();

	// public Optional<SubscriptionEntity> getBySubscriptionsMessageTypes(Long Id);
}
