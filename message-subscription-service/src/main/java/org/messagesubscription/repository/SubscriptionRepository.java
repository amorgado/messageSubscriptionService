package org.messagesubscription.repository;

import java.util.Optional;

import org.messagesubscription.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	public Optional<SubscriptionEntity> findByEmail(@Param("email") String email);

}
