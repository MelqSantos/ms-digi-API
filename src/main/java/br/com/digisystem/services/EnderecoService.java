package br.com.digisystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.EnderecoEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<EnderecoEntity> getAll(){
		return this.enderecoRepository.findAll();
	}
	
	public EnderecoEntity getById(int id) {
		return this.enderecoRepository.findById(id)
				.orElseThrow( ()-> new ObjNotFoundException("Elemento com ID " + id +" não foi localizado"));
	}
	
	public EnderecoEntity create(EnderecoEntity endereco) {
		return this.enderecoRepository.save(endereco);
	}
	
	public EnderecoEntity update(int id, EnderecoEntity endereco) {
		
		Optional<EnderecoEntity> endOptional = this.enderecoRepository.findById(id);
		
		if(endOptional.isPresent()) {
			
			EnderecoEntity endUpdate = endOptional.get();
			
			endUpdate.setLogradouro(endereco.getLogradouro());
			endUpdate.setNumero(endereco.getNumero());
			endUpdate.setCep(endereco.getCep());
			endUpdate.setComplemento(endereco.getComplemento());
			endUpdate.setBairro(endereco.getBairro());
			endUpdate.setUf(endereco.getUf());
			
			return this.enderecoRepository.save(endUpdate);
		}
		else {
			return null;
		}
	}
	
	public void delete(int id) {
		this.enderecoRepository.deleteById(id);
	}
}
