package br.com.digisystem.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.ProfessorRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProfessorServiceTests {

	@Autowired
	private ProfessorService professorService;
	
	@MockBean // Significa que o repository será simulado
	private ProfessorRepository professorRepository;
	
	@Test
	void getAllTest() {
		
		List<ProfessorEntity> professorSimulado = new ArrayList<>();
		ProfessorEntity professor = this.createValidUser(false);
		
		professorSimulado.add(professor);
		
		when( professorRepository.findAll()).thenReturn(professorSimulado);
		
		List<ProfessorEntity> lista = professorService.getAll();
		
		assertThat(professorSimulado.get(0).getNome())
			.isEqualTo(lista.get(0).getNome());
		
		assertThat(professorSimulado.get(0).getEmail())
			.isEqualTo(lista.get(0).getEmail());
	}
	
	@Test
	void getOneWhenFindUSerTest() {
		
		int id = 1;
		
		ProfessorEntity professorEntity = this.createValidUser(true);
		
		Optional<ProfessorEntity> optional = Optional.of(professorEntity);
		
		when (professorRepository.findById(id)).thenReturn(optional);
		
		ProfessorEntity professor = professorService.getOne(id);
		
		assertThat(professor.getNome())
			.isEqualTo(professorEntity.getNome());
	
		assertThat(professor.getEmail())
			.isEqualTo(professorEntity.getEmail());
	}
	
	@Test
	void getOneWhenNotFindUSerTest(){
		
		int id = 1;
		/* simulando quando não há um usuário no banco de dados , então 
		 * retorno uma exceção do tipo ObjNotFoundException*/
		when ( professorRepository.findById(id) )
			.thenThrow(  new ObjNotFoundException("Erro") );
		
		/*
		 * chama o getOne para verificar se o retono é do tipo ObjNotFoundException*/
	 	assertThrows(ObjNotFoundException.class, ()-> professorService.getOne(id) );
	}
	
	@Test
	void createTest() {
		// Crirar um método que vai ser retornado pela camada repository
		
		ProfessorEntity professorEntity = this.createValidUser(false);
		ProfessorEntity professorEntityRetorno = this.createValidUser(true);
		
		when(professorRepository.save(professorEntity)).thenReturn(professorEntityRetorno);
		
		// Testar a camada de serviço (create)
		ProfessorEntity professorSalvo = professorService.create(professorEntity);
		
		// Verificar se o retorno do getOne possui os valores do objeto criado no inicio do teste.
		assertThat(professorSalvo.getNome())
			.isEqualTo(professorEntity.getNome());

		assertThat(professorSalvo.getEmail())
			.isEqualTo(professorEntity.getEmail());
		
		assertThat(professorSalvo.getId()).isPositive();
	}
	
	private ProfessorEntity createValidUser(boolean isId) {
		
		ProfessorEntity profValid = new ProfessorEntity();
		profValid.setNome("Prof Fulano");
		profValid.setEmail("teste@teste.com");
	
		if(isId) {
			profValid.setId(1);
		}
		
		return profValid;
	}
}
