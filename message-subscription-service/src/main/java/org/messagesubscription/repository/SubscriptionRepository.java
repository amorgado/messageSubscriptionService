package org.messagesubscription.repository;

import java.util.Optional;

import org.messagesubscription.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	public Optional<SubscriptionEntity> findByEmail(@Param("email") String email);

	// @Query("Select sub.id, sub.email, subsMTypes.messageType.type from SubscriptionEntity sub JOIN SubscriptionsMessageTypesEntity subsMTypes ON sub.id = subsMTypes.subscription.id group by sub.id")
	// public List<Object[]> findBySubscriptionsMessageTypes();

	// @Query("Select sub.id, sub.email, subsMTypes.messageType.id, subsMTypes.messageType.type, count(subsMTypes.messageType.id) from SubscriptionEntity sub JOIN SubscriptionsMessageTypesEntity subsMTypes WHERE subsMTypes.messageType.id IN
	// (Select msType.id from MessageTypeEntity msType) group by subsMTypes.messageType.id")
	// public List<Object[]> findBySubscriptionsMessageTypes2();

	// @Query("Select sub.id, sub.email, subsMTypes.messageType.id, subsMTypes.messageType.type, count(subsMTypes.messageType.id) from SubscriptionEntity sub JOIN SubscriptionsMessageTypesEntity subsMTypes WHERE:subsMTypes.messageType.id
	// MEMBER OF subsMTypes.messageType.id group by subsMTypes.messageType.id")
	// public List<Object[]> findBySubscriptionsMessageTypes3();
}
