package br.com.digisystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.VendaEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	public List<VendaEntity> getAll() {
		return this.vendaRepository.findAll();
	}
	
	public VendaEntity getById(int id) {
		return this.vendaRepository.findById(id)
				.orElseThrow( () -> new ObjNotFoundException("Elemento com ID " + id +" não foi localizado"));
	}
	
	public VendaEntity create(VendaEntity venda) {
		return this.vendaRepository.save(venda);
	}
	
	public VendaEntity update (int id, VendaEntity venda) {
		Optional<VendaEntity> vendaOpt = this.vendaRepository.findById(id);
		
		if(vendaOpt.isPresent()) {
			VendaEntity vendaUpdate = vendaOpt.get();
			
			vendaUpdate.setValorTotal(venda.getValorTotal());
			
			return this.vendaRepository.save(vendaUpdate);
		}
		else {
			return null;
		}
	}
	
	public void delete(int id) {
		this.vendaRepository.deleteById(id);
	}
}
