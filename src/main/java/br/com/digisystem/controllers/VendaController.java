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

import br.com.digisystem.dtos.VendaDTO;
import br.com.digisystem.entities.VendaEntity;
import br.com.digisystem.services.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	public VendaService vendaService;
	
	@GetMapping
	public ResponseEntity<List<VendaDTO>> getAll(){
		List<VendaEntity> listEntity = this.vendaService.getAll();
		List<VendaDTO> listDTO = new ArrayList<>();
		
		for(int i = 0; i < listEntity.size(); i++) {
			listDTO.add(listEntity.get(i).toDTO());
		}
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VendaDTO> getById(@PathVariable int id){
		return ResponseEntity.ok().body(this.vendaService.getById(id).toDTO());
	}
	
	@PostMapping
	public ResponseEntity<VendaDTO> create(@RequestBody VendaDTO venda) {
		return ResponseEntity.ok().body(this.vendaService.create(venda.toEntity()).toDTO());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<VendaDTO> update(@PathVariable int id, @RequestBody VendaDTO venda){
		return ResponseEntity.ok().body(this.vendaService.update(id, venda.toEntity()).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id){
		this.vendaService.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
	
}
