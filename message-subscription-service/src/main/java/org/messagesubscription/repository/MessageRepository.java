package org.messagesubscription.repository;

import org.messagesubscription.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

}
