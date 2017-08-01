package org.messagesubscription.repository;

import java.util.Optional;

import org.messagesubscription.entity.MessageTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageTypeEntity, Long> {

	public Optional<MessageTypeEntity> findByType(@Param("type") String type);

	// @Query("SELECT count(msType.id) from MessageTypeEntity msType JOIN SubscriptionsMessageTypesEntity subMsType WHERE msType.id = :msTypeId")
	// public List<Object[]> findMessageTypesCount(@Param("messageTypeList") List<Long> messageTypesIds);

}
