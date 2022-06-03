package br.com.digisystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.digisystem.entities.UsuarioEntity;

@Repository
public interface UsuarioReposistory extends JpaRepository<UsuarioEntity, Integer> {

	// SELECT * FROM USUARIO WHERE NOME LIKE '%<nome>%'
	public List<UsuarioEntity> findByNomeContainsIgnoreCase(String nome);

	// SELECT * FROM USUARIO WHERE NOME LIKE '%<nome>'
	public List<UsuarioEntity> findByNomeStartsWith(String nome);

	// SELECT * FROM USUARIO WHERE NOME LIKE '<nome>%'
	public List<UsuarioEntity> findByNomeEndsWith(String nome);

	// SELECT * FROM USUARIO WHERE NOME LIKE '%<nome>%' AND email LIKE '%<email>%'
	public List<UsuarioEntity> findByNomeContainsAndEmailContains(String nome, String email);
	
	
	// Segunda forma de consultas (JPQL - Java Persistence Query Language)
	@Query("SELECT u FROM UsuarioEntity u WHERE u.nome LIKE %:nome%")
	public  List<UsuarioEntity> searchByNome(@Param("nome") String nome);
	
	// Modo nativo (SQL)
	@Query(value= "SELECT * FROM Usuarios WHERE nome LIKE %:nome%",
			nativeQuery = true)
	public  List<UsuarioEntity> searchByNomeNativo(@Param("nome") String nome);
	
	@Modifying // Anotação que permite modificar os dados no banco de dados
	@Query(value = "update usuarios set nome = :nome where id = :id",
			nativeQuery = true)
	public void updateUsuario(@Param("id") int id, @Param("nome") String nome);
}
