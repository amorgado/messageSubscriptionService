package org.messagesubscription.repository;

import java.util.Optional;

import org.messagesubscription.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	Optional<SubscriptionEntity> findByEmail(@Param("email") String email);

}
