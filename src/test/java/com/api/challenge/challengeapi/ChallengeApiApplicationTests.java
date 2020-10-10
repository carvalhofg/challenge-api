package com.api.challenge.challengeapi;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.qos.logback.core.net.SyslogOutputStream;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class ChallengeApiApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	public static String token = new String("");

	@Test
	@Order(1)
    public void AuthenticateAndSaveToken() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
			.content("{\"pass\":\"123456\",\"usrName\":\"responsibleusr1\"}")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String getResultContent = result.getResponse().getContentAsString();
			token = getResultContent.substring(10,165);


	}

	@Test
	@Order(2)
	public void ListAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/service-order/all"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)));

	}

	@Test
	@Order(3)
    public void GetServicesFromResponsible() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
			.content("{\"pass\":\"123456\",\"usrName\":\"responsibleusr1\"}")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String getResultContent = result.getResponse().getContentAsString();
			token = getResultContent.substring(10,165);

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order").header("Authorization", "Bearer "+token)
				.param("responsibleName", "Responsible1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)));

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order").header("Authorization", "Bearer "+token)
				.param("responsibleName", "Responsible2"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());


	}

	@Test
	@Order(4)
    public void UpdateServicesFromResponsible() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
			.content("{\"pass\":\"123456\",\"usrName\":\"responsibleusr1\"}")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String getResultContent = result.getResponse().getContentAsString();
			token = getResultContent.substring(10,165);

			mockMvc.perform(MockMvcRequestBuilders.put("/service-order/1").header("Authorization", "Bearer "+token)
				.content("{\"orderStatus\":\"RUNNING\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

			mockMvc.perform(MockMvcRequestBuilders.put("/service-order/2").header("Authorization", "Bearer "+token)
				.content("{\"orderStatus\":\"RUNNING\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order").header("Authorization", "Bearer "+token)
				.param("responsibleName", "Responsible1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[?(@.orderStatus==\"RUNNING\")]")
                        .exists());

	}

	@Test
	@Order(5)
    public void CreateServiceOrder() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
			.content("{\"pass\":\"123456\",\"usrName\":\"responsibleusr1\"}")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String getResultContent = result.getResponse().getContentAsString();
			token = getResultContent.substring(10,165);

			mockMvc.perform(MockMvcRequestBuilders.post("/service-order").header("Authorization", "Bearer "+token)
				.content("{\"customerName\":\"Customer1\",\"issue\":\"issueDescrip\",\"productModel\":\"Model1\",\"productType\":\"Type1\",\"responsibleName\":\"Responsible2\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order/all"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(3)));
	}

	@Test
	@Order(6)
    public void CreateServiceOrderFollowUp() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
			.content("{\"pass\":\"123456\",\"usrName\":\"responsibleusr2\"}")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String getResultContent = result.getResponse().getContentAsString();
			token = getResultContent.substring(10,165);

			mockMvc.perform(MockMvcRequestBuilders.post("/service-order/3/followup").header("Authorization", "Bearer "+token)
				.content("{\"description\":\"FollowUp1\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());

			mockMvc.perform(MockMvcRequestBuilders.post("/service-order/3/followup").header("Authorization", "Bearer "+token)
				.content("{\"description\":\"FollowUp2\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order?responsibleName=Responsible2").header("Authorization", "Bearer "+token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*.orderFollowUps.*", Matchers.hasSize(2)));
	}
	
	@Test
	@Order(7)
    public void DeleteServicesFromResponsible() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
			.content("{\"pass\":\"123456\",\"usrName\":\"responsibleusr1\"}")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String getResultContent = result.getResponse().getContentAsString();
			token = getResultContent.substring(10,165);

			mockMvc.perform(MockMvcRequestBuilders.delete("/service-order/1").header("Authorization", "Bearer "+token)
				.param("responsibleName", "Responsible1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order").header("Authorization", "Bearer "+token)
				.param("responsibleName", "Responsible2"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());

			mockMvc.perform(MockMvcRequestBuilders.get("/service-order/all"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)));
	}
}
