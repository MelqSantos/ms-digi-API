package br.com.digisystem.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digisystem.dtos.UsuarioDTO;
import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.services.CsvExportService;
import br.com.digisystem.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CsvExportService csvExportService;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> getAll() {
		
		List<UsuarioEntity> lista = this.usuarioService.getAll();
		
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		
		for(int i = 0; i < lista.size(); i++) {
			listaDTO.add( lista.get(i).toDTO());
		}
		return ResponseEntity.ok().body( listaDTO );
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> getOne(@PathVariable int id) {
		
		return ResponseEntity.ok().body(this.usuarioService.getOne(id).toDTO()); 
	}
	
	@GetMapping("/get-by-nome/{nome}")
	public ResponseEntity<List<UsuarioDTO>> getByNome(@PathVariable String nome){
		
		List<UsuarioEntity> lista = this.usuarioService.getByName(nome);
		
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		
		for(int i = 0; i < lista.size(); i++) {
			listaDTO.add( lista.get(i).toDTO());
		}
		return ResponseEntity.ok().body( listaDTO );
	}
	
	@GetMapping("/export")
    public ResponseEntity<Void> getAllUsersInCsv(HttpServletResponse servletResponse) throws IOException {
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        servletResponse.setHeader("Content-Type", "text/csv;charset=UTF-8");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"users_" + timeStamp +".csv\"");
        csvExportService.writeUsersToCsv(servletResponse.getWriter());
        
        return ResponseEntity.ok().build();
    }

	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO usuario) {
		
		// Converte um usuarioDTO para usuarioEntity
		UsuarioEntity usuarioEntity = usuario.toEntity();
		
		// Cria uma usuario no banco uasando o UsuarioDTO convertido
		UsuarioEntity usuarioEntitySalvo = this.usuarioService.create(usuarioEntity);
		
		// Retorna o usuario DTO convertido
		return ResponseEntity.ok().body(usuarioEntitySalvo.toDTO());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable int id,
			@RequestBody UsuarioDTO usuario) {
		
		UsuarioEntity usuarioEntitySalvo = this.usuarioService.update(id, usuario.toEntity());
		
		return ResponseEntity.ok().body( usuarioEntitySalvo.toDTO() );
	}
	
	@PatchMapping("update/{id}")
	public ResponseEntity<Void> updateUsuario(@PathVariable int id,
			@RequestBody UsuarioDTO dto) {
		
		this.usuarioService.updateUsuario(id, dto.getNome());
		
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		this.usuarioService.delete(id);
		
		// Retorna um status 200 (OK) mesmo sendo um método Void
		return ResponseEntity.ok().build();
	}
}
