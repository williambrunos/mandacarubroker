package com.mandacarubroker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class MandacarubrokerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetStocks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetStockByIdNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{id}", "teste_falha"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testCreateStock() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/stocks")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"companyName\": \"Test Stock\", \"symbol\": \"TST4\", \"price\":  45.2}"))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void testUpdateStock() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/stocks/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"companyName\": \"Test Stock\", \"symbol\": \"TST4\", \"price\":  45}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testDeleteStock() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/{id}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
