package com.jobs.app;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jobs.app.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.thymeleaf.spring5.expression.Mvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class JobsApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mvc;

	private static WireMockServer server = new WireMockServer(8080);

	@BeforeAll
	static void init() {
		server.start();
	}

	@AfterAll
	static void end() {
		server.stop();
	}

	@Test
	void testAppointmentsGetAPI() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/appointments"))
			.andReturn();
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testJobSeekersGetAPI() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/jobSeekers"))
			.andReturn();
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testConsultantsGetAPI() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/consultants"))
			.andReturn();
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testCountriesGetAPI() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/parameters/countries"))
			.andReturn();
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testSectorsGetAPI() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/parameters/sectors"))
			.andReturn();
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testAvailabilityGetAPI() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/consultants/availability/8129389"))
			.andReturn();
		Assertions.assertEquals(404, result.getResponse().getStatus());
		Assertions.assertNotNull(result.getResponse().getContentAsString());
	}

}
