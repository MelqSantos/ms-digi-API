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

import br.com.digisystem.dtos.ProdutoDTO;
import br.com.digisystem.entities.ProdutoEntity;
import br.com.digisystem.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> getAll(){
		
		List<ProdutoEntity> listEntity = this.produtoService.getAll();
		List<ProdutoDTO> listDTO = new ArrayList<>();
		
		for(int i = 0; i < listEntity.size(); i++) {
			listDTO.add(listEntity.get(i).toDTO());
		}
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> getByID(@PathVariable int id){
		return ResponseEntity.ok().body(this.produtoService.getById(id).toDTO());
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> create(@RequestBody ProdutoDTO produto){
		return ResponseEntity.ok().body(this.produtoService.create(produto.toEntity()).toDTO());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProdutoDTO> update(@PathVariable int id, @RequestBody ProdutoDTO produto){
		return ResponseEntity.ok().body(this.produtoService.update(id, produto.toEntity()).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ProdutoDTO> delete(@PathVariable int id){
		this.produtoService.delete(id);
		
		return ResponseEntity.ok().build();
	}
}
