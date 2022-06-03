package br.com.digisystem.respositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.repositories.UsuarioReposistory;
import br.com.digisystem.utils.UsuarioUtil;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTests extends UsuarioUtil {

	@Autowired
	private UsuarioReposistory usuarioRepository;

	// Variável que manipula o banco de dados H2
	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findAllTest() {
//		UsuarioEntity usuarioEntity = new UsuarioEntity();
//		usuarioEntity.setEmail("teste@teste.com");
//		usuarioEntity.setNome("Nome teste");

		UsuarioEntity usuarioEntity = createValidUser();

		// Insere o objeto no banco H2
		testEntityManager.persist(usuarioEntity);

		// Realiza o teste
		List<UsuarioEntity> lista = usuarioRepository.findAll();

		assertThat(lista.size()).isEqualTo(1);
	}

	@Test
	void findByIdWhenFoundUser() {

		UsuarioEntity usuarioEntity = createValidUser();

		// Insere o objeto no banco H2
		testEntityManager.persist(usuarioEntity);

		Optional<UsuarioEntity> usuario = usuarioRepository.findById(usuarioEntity.getId());

		assertThat(usuario).isPresent();
	}

	@Test
	void findByIdWhenNotFoundUser() {

		Optional<UsuarioEntity> usuario = usuarioRepository.findById(1);

		assertThat(usuario).isEmpty();
	}

	@Test
	void saveTest() {
		UsuarioEntity usuarioEntity = createValidUser();

		// Executa o teste
		UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);

		assertThat(usuarioSalvo.getId()).isPositive();
		assertThat(usuarioSalvo.getEmail()).isEqualTo(usuarioEntity.getEmail());
		assertThat(usuarioSalvo.getNome()).isEqualTo(usuarioEntity.getNome());
	}

	@Test
	void deleteByIdTest() {
		UsuarioEntity usuarioEntity = createValidUser();

		// Insere no banco H2
		testEntityManager.persist(usuarioEntity);

		// Deleta o usuário
		usuarioRepository.deleteById(usuarioEntity.getId());

		// Procura o usuário deletado
		Optional<UsuarioEntity> deletado = usuarioRepository.
				findById(usuarioEntity.getId());

		assertThat(deletado).isEmpty();
	}

	@Test
	void findByNomeContainsTest() {
		UsuarioEntity usuarioEntity = createValidUser();

		// Insere no banco H2
		testEntityManager.persist(usuarioEntity);
		
		List<UsuarioEntity> lista = usuarioRepository
				.findByNomeContainsIgnoreCase(usuarioEntity.getNome());
		
		assertThat(lista.size()).isEqualTo(1);
		assertThat(lista.get(0).getNome()).isEqualTo(usuarioEntity.getNome());
	}
	
}
