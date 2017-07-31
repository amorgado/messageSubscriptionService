package org.messagesubscription;

import java.util.ArrayList;
import java.util.List;

import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.entity.SubscriptionEntity;
import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.messagesubscription.enums.MessageTypeEnum;
import org.messagesubscription.repository.MessageRepository;
import org.messagesubscription.repository.MessageTypeRepository;
import org.messagesubscription.repository.SubscriptionMessageTypeRepository;
import org.messagesubscription.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MessageSubscriptionServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(MessageSubscriptionServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MessageSubscriptionServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(MessageTypeRepository messageTypeRepo, MessageRepository messageRepo, SubscriptionRepository subscriptionRepo, SubscriptionMessageTypeRepository subscriptionMessageTypeRepo) {
		logger.trace("Entering init method");
		return (evt) -> {
			messageTypeRepo.deleteAll();
			MessageTypeEntity redType = new MessageTypeEntity(MessageTypeEnum.RED.name());
			MessageTypeEntity blueType = new MessageTypeEntity(MessageTypeEnum.BLUE.name());
			MessageTypeEntity greenType = new MessageTypeEntity(MessageTypeEnum.GREEN.name());
			MessageTypeEntity yellowType = new MessageTypeEntity(MessageTypeEnum.YELLOW.name());
			List<MessageTypeEntity> messageTypes = new ArrayList<MessageTypeEntity>();
			messageTypes.add(redType);
			messageTypes.add(blueType);
			messageTypes.add(greenType);
			messageTypes.add(yellowType);
			messageTypeRepo.saveAll(messageTypes);

			messageRepo.deleteAll();
			messageRepo.saveAndFlush(new MessageEntity("Red color is hot.", redType));
			messageRepo.saveAndFlush(new MessageEntity("Red color is very hot.", redType));
			messageRepo.saveAndFlush(new MessageEntity("Blue color is cold.", blueType));

			subscriptionRepo.deleteAll();
			SubscriptionEntity subscriptionEntity1 = new SubscriptionEntity("alexei_morgado@yahoo.com");
			subscriptionRepo.saveAndFlush(subscriptionEntity1);

			subscriptionMessageTypeRepo.deleteAll();
			SubscriptionsMessageTypesEntity subscription1MessageTypeEntity1 = new SubscriptionsMessageTypesEntity(redType, subscriptionEntity1);
			SubscriptionsMessageTypesEntity subscription1MessageTypeEntity2 = new SubscriptionsMessageTypesEntity(blueType, subscriptionEntity1);
			List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypesEntities1 = new ArrayList<SubscriptionsMessageTypesEntity>();
			subscriptionsMessageTypesEntities1.add(subscription1MessageTypeEntity1);
			subscriptionsMessageTypesEntities1.add(subscription1MessageTypeEntity2);
			subscriptionMessageTypeRepo.saveAll(subscriptionsMessageTypesEntities1);

			// subscriptionRepo.saveAndFlush(subscriptionEntity1);

			// SubscriptionsMessageTypesEntity subscription2MessageTypeEntity1 = new SubscriptionsMessageTypesEntity(greenType);
			// SubscriptionsMessageTypesEntity subscription2MessageTypeEntity2 = new SubscriptionsMessageTypesEntity(yellowType);
			// List<SubscriptionsMessageTypesEntity> subscriptionsMessageTypesEntities2 = new ArrayList<SubscriptionsMessageTypesEntity>();
			// subscriptionsMessageTypesEntities1.add(subscription2MessageTypeEntity1);
			// subscriptionsMessageTypesEntities1.add(subscription2MessageTypeEntity2);
			// subscriptionMessageTypeRepo.saveAll(subscriptionsMessageTypesEntities2);
			//
			// SubscriptionEntity subscriptionEntity2 = new SubscriptionEntity("roberto_gonzalez@yahoo.com", subscriptionsMessageTypesEntities2);
			// subscriptionRepo.saveAndFlush(subscriptionEntity2);

		};
	}

}
