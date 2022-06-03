package br.com.digisystem.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.UsuarioReposistory;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UsuarioServiceTests {

	@Autowired
	private UsuarioService usuarioService;
	
	@MockBean // Significa que o repository será simulado
	private UsuarioReposistory usuarioRepository;
	
	@Test
	void getAllTest() {
		
		List<UsuarioEntity> usuarioSimulado = new ArrayList<>();
		UsuarioEntity usuario = this.createValidUser(false);
		
		usuarioSimulado.add(usuario);
		
		when( usuarioRepository.findAll()).thenReturn(usuarioSimulado);
		
		List<UsuarioEntity> lista = usuarioService.getAll();
		
		assertThat(usuarioSimulado.get(0).getNome())
			.isEqualTo(lista.get(0).getNome());
		
		assertThat(usuarioSimulado.get(0).getEmail())
			.isEqualTo(lista.get(0).getEmail());
	}
	
	@Test
	void getOneWhenFindUSerTest() {
		
		UsuarioEntity usuarioEntity = this.createValidUser(true);
		
		Optional<UsuarioEntity> optional = Optional.of(usuarioEntity);
		
		when (usuarioRepository.findById(usuarioEntity.getId())).thenReturn(optional);
		
		UsuarioEntity usuario = usuarioService.getOne(usuarioEntity.getId());
		
		assertThat(usuario.getNome())
			.isEqualTo(usuarioEntity.getNome());
	
		assertThat(usuario.getEmail())
			.isEqualTo(usuarioEntity.getEmail());
	}
	
	@Test
	void getOneWhenNotFindUSerTest(){
		
		int id = 1;
		/* simulando quando não há um usuário no banco de dados , então 
		 * retorno uma exceção do tipo ObjNotFoundException*/
		when ( usuarioRepository.findById(id) )
			.thenThrow(  new ObjNotFoundException("Erro") );
		
		/*
		 * chama o getOne para verificar se o retono é do tipo ObjNotFoundException*/
	 	assertThrows(ObjNotFoundException.class, ()-> usuarioService.getOne(id) );
	}
	
	@Test
	void getByNome() {
		
		// Cria a lista simulada
		UsuarioEntity usuarioValid = this.createValidUser(true);
		List<UsuarioEntity> lista = Arrays.asList(usuarioValid, usuarioValid);
		
		// Mocka o método
		when(usuarioRepository.searchByNomeNativo("nome")).thenReturn(lista);
		
		// Verifica se a lista não é vazia
		assertThat(lista).isNotEmpty();
		// Verifica se não estourou nenhum erro no método testado
		assertDoesNotThrow( () -> usuarioRepository.searchByNomeNativo("nome"));
	}
	
	@Test
	void createTest() {
		// Crirar um método que vai ser retornado pela camada repository
		
		// Chama a função para criar novas instâncias de usuários
		UsuarioEntity usuarioEntity = this.createValidUser(false);
		UsuarioEntity usuarioEntityRetorno = this.createValidUser(true);
		
		when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntityRetorno);
		
		// Testa a camada de serviço (create)
		UsuarioEntity usuarioSalvo = usuarioService.create(usuarioEntity);
		
		// Verifica se o retorno do getOne possui os valores do objeto criado no inicio do teste.
		assertThat(usuarioSalvo.getNome())
			.isEqualTo(usuarioEntity.getNome());

		assertThat(usuarioSalvo.getEmail())
			.isEqualTo(usuarioEntity.getEmail());
		
		assertThat(usuarioSalvo.getId()).isPositive();
	}
	
	@Test
	void deleteTest() {
		
		//execução
		assertDoesNotThrow( () -> usuarioService.delete(1) );
		
		// verifica se o método usuarioRepository.deleteById() foi executado apenas uma vez
		verify(usuarioRepository, times(1) ).deleteById(1);
	}
	
	// Testa o update de um usuário existente
	@Test
	void updateWhenFoundUser() {
		
		UsuarioEntity usuarioValid = this.createValidUser(true);
		
		when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioValid));
		when(usuarioRepository.save(usuarioValid)).thenReturn(usuarioValid);
		
		// Executa o teste
		UsuarioEntity usuarioAlterado = usuarioService.update(1, usuarioValid);
		
		// VErifica se o teste deu certo
		assertThat(usuarioAlterado.getNome())
			.isEqualTo(usuarioValid.getNome());

		assertThat(usuarioAlterado.getEmail())
			.isEqualTo(usuarioValid.getEmail());
	}
	
	// Testa se o usuário a ser editado é realmente nulo
	@Test
	void updateWhenNotFoundUser() {
		when(usuarioRepository.findById(1)).thenReturn(Optional.empty());
		
		UsuarioEntity usuarioAlterado = usuarioService.update(1, null);
		
		assertThat(usuarioAlterado).isNull();
	}
	
	private UsuarioEntity createValidUser(boolean isId) {
		
		int id = 1;
		
		UsuarioEntity usuarioValid = new UsuarioEntity();
		usuarioValid.setEmail("teste@teste.com");
		usuarioValid.setNome("Nome teste");
		
		if(isId == true) {
			usuarioValid.setId(id);
		}
		
		return usuarioValid;
	}
	
	
}


