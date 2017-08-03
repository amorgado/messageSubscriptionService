package org.messagesubscription.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.messagesubscription.model.MessageType;
import org.messagesubscription.model.Subscription;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.springframework.http.MediaType;

public class SubscriptionControllerTest extends BaseControllerTest {

	@Test
	public void findSubscriptionsTest() throws Exception {
		this.mvc.perform(get(MessageSubscriptionConstants.SUBSRIPTIONS_PATH).contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(containsString(MessageSubscriptionConstants.EMAIL))).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void createSubscriptionsTest() throws Exception {
		List<MessageType> messageTypes = new ArrayList<MessageType>();
		messageTypes.add(new MessageType((long) 1));
		Subscription subscription = new Subscription("alexei_morgado2@yahoo.com", messageTypes);
		String strSubscription = objectMapper.writeValueAsString(subscription);
		this.mvc.perform(post(MessageSubscriptionConstants.SUBSRIPTIONS_PATH).content(strSubscription).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void updateSubscriptionsTest() throws Exception {
		List<MessageType> messageTypes = new ArrayList<MessageType>();
		messageTypes.add(new MessageType((long) 3));
		Subscription subscription = new Subscription((long) 1, MessageSubscriptionConstants.EMAIL, messageTypes);
		String strSubscription = objectMapper.writeValueAsString(subscription);

		this.mvc.perform(put(MessageSubscriptionConstants.SUBSRIPTIONS_PATH).content(strSubscription).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
	}

}
