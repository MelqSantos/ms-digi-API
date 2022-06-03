package br.com.digisystem.controllers;

import java.util.ArrayList;
import java.util.List;

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

import br.com.digisystem.dtos.EnderecoDTO;
import br.com.digisystem.entities.EnderecoEntity;
import br.com.digisystem.services.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;
	
	// Buscar todos
	@GetMapping
	public ResponseEntity<List<EnderecoDTO>> getAll(){
		
		List<EnderecoEntity> listEntity = this.enderecoService.getAll();
		
		List<EnderecoDTO> listDTO = new ArrayList<>();
		
		for(int i = 0; i < listEntity.size(); i++) {
			listDTO.add(listEntity.get(i).toDTO());
		}
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	// Buscar pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoDTO> getById(@PathVariable int id){
		return ResponseEntity.ok().body(this.enderecoService.getById(id).toDTO()); 
	}
	
	// Adicionar um novo endereço
	@PostMapping
	public ResponseEntity<EnderecoDTO> create(@RequestBody EnderecoDTO endereco){
		return ResponseEntity.ok().body( this.enderecoService.create(endereco.toEntity()).toDTO());
	}
	
	// Atualiza endereço existente
	@PatchMapping("/{id}")
	public ResponseEntity<EnderecoDTO> update(@PathVariable int id,  @RequestBody EnderecoDTO endereco){
		
		EnderecoEntity enderecoSalvo = this.enderecoService.update(id, endereco.toEntity());
		
		return ResponseEntity.ok().body(enderecoSalvo.toDTO());
	}

	// Deleta um endereço
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id){
		this.enderecoService.delete(id);
		
		return ResponseEntity.ok().build();
	}
}
