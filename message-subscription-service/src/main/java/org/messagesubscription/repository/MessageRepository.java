package org.messagesubscription.repository;

import java.util.Optional;

import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

	public Optional<MessageTypeEntity> findByDescription(@Param("description") String description);

}
