package org.messagesubscription.repository;

import org.messagesubscription.entity.MessageTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MessageTypeRepository extends JpaRepository<MessageTypeEntity, Long> {

	public MessageTypeEntity findByType(@Param("type") String type);

}
