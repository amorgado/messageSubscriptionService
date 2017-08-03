package org.messagesubscription.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.messagesubscription.model.Message;
import org.messagesubscription.model.MessageType;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.springframework.http.MediaType;

public class MessageControllerTest extends BaseControllerTest {

	@Test
	public void findMessagesTest() throws Exception {
		this.mvc.perform(get(MessageSubscriptionConstants.MESSAGES_PATH).contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(containsString(MessageSubscriptionConstants.MESSAGE))).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void createMessageTest() throws Exception {
		Message message = new Message(MessageSubscriptionConstants.MESSAGE, new MessageType((long) 2));
		String strMessage = objectMapper.writeValueAsString(message);
		this.mvc.perform(post(MessageSubscriptionConstants.MESSAGES_PATH).content(strMessage).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
	}

}
