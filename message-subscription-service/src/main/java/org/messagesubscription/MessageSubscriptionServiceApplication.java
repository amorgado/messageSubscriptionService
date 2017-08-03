package org.messagesubscription;

import org.messagesubscription.entity.MessageEntity;
import org.messagesubscription.entity.MessageTypeEntity;
import org.messagesubscription.entity.SubscriptionEntity;
import org.messagesubscription.entity.SubscriptionsMessageTypesEntity;
import org.messagesubscription.enums.MessageTypeEnum;
import org.messagesubscription.repository.MessageRepository;
import org.messagesubscription.repository.MessageTypeRepository;
import org.messagesubscription.repository.SubscriptionMessageTypeRepository;
import org.messagesubscription.repository.SubscriptionRepository;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessageSubscriptionServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(MessageSubscriptionServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MessageSubscriptionServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(MessageTypeRepository messageTypeRepo, MessageRepository messageRepo, SubscriptionRepository subscriptionRepo, SubscriptionMessageTypeRepository subscriptionMessageTypeRepo) {
		logger.trace("Entering init method");
		return (evt) -> {
			MessageTypeEntity redType = new MessageTypeEntity(MessageTypeEnum.RED.name());
			MessageTypeEntity blueType = new MessageTypeEntity(MessageTypeEnum.BLUE.name());
			MessageTypeEntity greenType = new MessageTypeEntity(MessageTypeEnum.GREEN.name());
			MessageTypeEntity yellowType = new MessageTypeEntity(MessageTypeEnum.YELLOW.name());
			messageTypeRepo.save(redType);
			messageTypeRepo.save(blueType);
			messageTypeRepo.save(greenType);
			messageTypeRepo.save(yellowType);
			messageTypeRepo.flush();

			messageRepo.save(new MessageEntity(MessageSubscriptionConstants.MESSAGE, redType));
			messageRepo.flush();

			SubscriptionEntity subscriptionEntity = new SubscriptionEntity(MessageSubscriptionConstants.EMAIL);
			subscriptionRepo.save(subscriptionEntity);
			subscriptionRepo.flush();

			subscriptionMessageTypeRepo.save(new SubscriptionsMessageTypesEntity(redType, subscriptionEntity));
			subscriptionMessageTypeRepo.flush();

		};
	}

}
