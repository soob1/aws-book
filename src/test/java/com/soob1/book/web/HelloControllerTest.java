package com.soob1.book.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void beforeAll() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void return_hello() throws Exception {
		String hello = "hello";

		mvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string(hello));
	}

	@Test
	void return_hello_dto() throws Exception {
		String name = "hello";
		int amount = 1000;

		mvc.perform(get("/hello/dto")
				.param("name", name)
				.param("amount", String.valueOf(amount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(jsonPath("$.amount").value(amount));
	}
}