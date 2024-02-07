package com.mandacarubroker;

import jakarta.validation.constraints.Null;
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
	void shouldGetAllStocks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldNotGetStockByInexistentId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{id}", "teste_falha"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void shouldCreateStock() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/stocks")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"companyName\": \"Test Stock\", \"symbol\": \"TST4\", \"price\":  45.2}"))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void shouldUpdateStockById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/stocks/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"companyName\": \"Test Stock\", \"symbol\": \"TST4\", \"price\":  45}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldNotUpdateStockByInexistintId() throws Exception {
		// Perform a PUT request with a non-existent ID
		mockMvc.perform(MockMvcRequestBuilders.put("/stocks/{id}", "")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"name\": \"Updated Stock\", \"symbol\": \"UPD\" }"))
						.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void shouldDeleteStockById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/{id}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldNotAllowDeleteAllStocks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/stocks"))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}
}
