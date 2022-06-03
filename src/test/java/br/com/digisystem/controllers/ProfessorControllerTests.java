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

import br.com.digisystem.dtos.ProfessorDTO;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class ProfessorControllerTests {

	@Autowired
	private MockMvc mockmvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	static int lastIdUser = 0;
	
	@Test
	void getAllTest() throws Exception {
		ResultActions response =  mockmvc.perform(
				get("/professores")
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProfessorDTO[] lista = mapper.readValue(resultStr, ProfessorDTO[].class);
		
		// Verifica se a lista não é vazia
		assertThat(lista).isNotEmpty();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
		
	}
	
	@Test
	void getOne() throws Exception {
		
		int id = 1;
		
		ResultActions response =  mockmvc.perform(
				get("/professores/" + id)
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProfessorDTO professor = mapper.readValue(resultStr, ProfessorDTO.class);
		
		assertThat(professor.getId()).isEqualTo(id);
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void creatTest() throws Exception{
		// Criar um ProfessorDTO para enviar no corpo da requisição
		
		ProfessorDTO prof = new ProfessorDTO();
		prof.setNome("Prof Melqui JUNIT");
		prof.setEmail("junit@gmail.com");
		
		ResultActions response =  mockmvc.perform(
				post("/professores")
				.contentType("application/json")
				.content(mapper.writeValueAsString(prof))
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProfessorDTO profSalvo = mapper.readValue(resultStr, ProfessorDTO.class);
		
		assertThat(profSalvo.getId()).isPositive();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
		
		// Seta o último id criado para usar nos outros testes
		lastIdUser = profSalvo.getId();
	}
	
	@Test
	void updateTest() throws Exception{
		
		ProfessorDTO prof = new ProfessorDTO();
		
		prof.setNome("Prof Melqui JUNIT Alterado");
		prof.setEmail("junit@gmail.com");
		
		int id = 8;
		
		ResultActions response =  mockmvc.perform(
				patch("/professores/" + id)
				.contentType("application/json")
				.content(mapper.writeValueAsString(prof))
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		ProfessorDTO profAlterado = mapper.readValue(resultStr, ProfessorDTO.class);
		
		assertThat(profAlterado.getId()).isEqualTo(id);
		assertThat(profAlterado.getNome()).isEqualTo(prof.getNome());
		assertThat(profAlterado.getEmail()).isEqualTo(prof.getEmail());
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void deleteTest() throws Exception{
		
		int id = lastIdUser;
		ResultActions response =  mockmvc.perform(
				delete("/professores/" + id)
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
}
