package br.com.digisystem.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.digisystem.dtos.ProdutoDTO;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class ProdutoControllerTests {
	
	@Autowired
	private MockMvc mockmvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	static int lastIdProd = 0;
	
	@Test
	void getAllTest() throws Exception {
		ResultActions response =  mockmvc.perform(
				get("/produtos")
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProdutoDTO[] lista = mapper.readValue(resultStr, ProdutoDTO[].class);
		
		assertThat(lista).isNotEmpty();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void getOne() throws Exception {
		
		int id = 1;
		
		ResultActions response =  mockmvc.perform(
				get("/produtos/" + id)
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProdutoDTO produto = mapper.readValue(resultStr, ProdutoDTO.class);
		
		assertThat(produto.getId()).isEqualTo(id);
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void creatTest() throws Exception{
		// Criar um ProfessorDTO para enviar no corpo da requisição
		
		ProdutoDTO produto = new ProdutoDTO();
		produto.setNome("Produto JUNIT");
		produto.setDescricao("Teste Junit");
		
		ResultActions response =  mockmvc.perform(
				post("/produtos")
				.contentType("application/json")
				.content(mapper.writeValueAsString(produto))
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProdutoDTO produtoSalvo = mapper.readValue(resultStr, ProdutoDTO.class);
		
		assertThat(produtoSalvo.getId()).isPositive();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
		
		// Seta o último id criado para usar nos outros testes
		lastIdProd = produtoSalvo.getId();
	}
	
	@Test
	void updateTest() throws Exception{
		
		ProdutoDTO produto = new ProdutoDTO();
		produto.setNome("Produto JUNIT");
		produto.setDescricao("Teste Junit");
		produto.setValor(250);
		
		int id = 5;
		
		ResultActions response =  mockmvc.perform(
				patch("/produtos/" + id)
				.contentType("application/json")
				.content(mapper.writeValueAsString(produto))
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProdutoDTO produtoAlterado = mapper.readValue(resultStr, ProdutoDTO.class);
		
		assertThat(produtoAlterado.getId()).isEqualTo(id);
		assertThat(produtoAlterado.getNome()).isEqualTo(produto.getNome());
		assertThat(produtoAlterado.getDescricao()).isEqualTo(produto.getDescricao());
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void deleteTest() throws Exception{
		
		int id = lastIdProd;
		ResultActions response =  mockmvc.perform(
				delete("/produtos/" + id)
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

}
