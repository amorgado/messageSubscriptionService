package org.messagesubscription.repository;

import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionMessageTypeRepository extends JpaRepository<SubscriptionsMessageTypesEntity, Long> {

}
