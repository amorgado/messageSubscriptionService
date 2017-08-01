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
			MessageTypeEntity redType = new MessageTypeEntity(MessageTypeEnum.RED.name());
			MessageTypeEntity blueType = new MessageTypeEntity(MessageTypeEnum.BLUE.name());
			MessageTypeEntity greenType = new MessageTypeEntity(MessageTypeEnum.GREEN.name());
			MessageTypeEntity yellowType = new MessageTypeEntity(MessageTypeEnum.YELLOW.name());
			messageTypeRepo.save(redType);
			messageTypeRepo.save(blueType);
			messageTypeRepo.save(greenType);
			messageTypeRepo.save(yellowType);
			messageTypeRepo.flush();

			messageRepo.save(new MessageEntity("Red color is hot.", redType));
			messageRepo.save(new MessageEntity("Red color is very hot.", redType));
			messageRepo.save(new MessageEntity("Blue color is cold.", blueType));
			messageRepo.flush();

			SubscriptionEntity subscriptionEntity1 = new SubscriptionEntity("alexei_morgado@yahoo.com");
			subscriptionRepo.save(subscriptionEntity1);

			subscriptionMessageTypeRepo.save(new SubscriptionsMessageTypesEntity(redType, subscriptionEntity1));
			subscriptionMessageTypeRepo.save(new SubscriptionsMessageTypesEntity(blueType, subscriptionEntity1));
			subscriptionMessageTypeRepo.save(new SubscriptionsMessageTypesEntity(yellowType, subscriptionEntity1));

			SubscriptionEntity subscriptionEntity2 = new SubscriptionEntity("jorge_gonzalez@gmail.com");
			subscriptionRepo.save(subscriptionEntity2);
			subscriptionRepo.flush();

			subscriptionMessageTypeRepo.save(new SubscriptionsMessageTypesEntity(greenType, subscriptionEntity2));
			subscriptionMessageTypeRepo.save(new SubscriptionsMessageTypesEntity(yellowType, subscriptionEntity2));
			subscriptionMessageTypeRepo.flush();

		};
	}

}
