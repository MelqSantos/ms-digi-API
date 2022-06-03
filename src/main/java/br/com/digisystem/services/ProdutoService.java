package br.com.digisystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.ProdutoEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<ProdutoEntity> getAll() {
		return this.produtoRepository.findAll();
	}
	
	public ProdutoEntity getById(int id) {
		return this.produtoRepository.findById(id)
				.orElseThrow( () -> new ObjNotFoundException("O elemento de ID " + id + " não localizado"));
	}
	
	public ProdutoEntity create(ProdutoEntity produto) {
		return this.produtoRepository.save(produto);
	}
	
	public ProdutoEntity update(int id, ProdutoEntity produto) {
		Optional<ProdutoEntity> produtoOpt = this.produtoRepository.findById(id);
		
		if(produtoOpt.isPresent()) {
			ProdutoEntity prodUpdate = produtoOpt.get();
			
			prodUpdate.setValor(produto.getValor());
			prodUpdate.setNome(produto.getNome());
			prodUpdate.setDescricao(produto.getDescricao());
			
			return this.produtoRepository.save(prodUpdate);
		}
		else {
			return null;
		}
	}
	
	public void delete(int id) {
		this.produtoRepository.deleteById(id);
	}
	

}
