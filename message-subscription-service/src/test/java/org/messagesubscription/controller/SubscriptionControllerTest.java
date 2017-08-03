package org.messagesubscription.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.messagesubscription.controller.SubscriptionController;
import org.messagesubscription.utils.MessageSubscriptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
@SpringBootTest
public class SubscriptionControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void findSubscriptionsTest() throws Exception {
		this.mvc.perform(get(MessageSubscriptionConstants.SUBSRIPTIONS_PATH).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
