package org.messagesubscription.repository;

import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionMessageTypeRepository extends JpaRepository<SubscriptionsMessageTypesEntity, Long> {

	@Query("SELECT count(subMsType.messageType.id) FROM SubscriptionsMessageTypesEntity subMsType WHERE subMsType.messageType.id = :msTypeId GROUP BY subMsType.messageType.id")
	public int findNumberOfMessageTypesBySubscription(@Param("msTypeId") Long msTypeId);
}
