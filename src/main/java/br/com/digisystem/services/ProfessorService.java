package br.com.digisystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.repositories.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	public List<ProfessorEntity> getAll(){
		return this.professorRepository.findAll(); 
	}
	
	public ProfessorEntity getOne(int id) {
		
		Optional<ProfessorEntity> profOptional = this.professorRepository.findById(id);
		
		if(profOptional.isPresent()) {
			return profOptional.get();
		}
		else {
			return null;
		}
	}
	
	public ProfessorEntity create(ProfessorEntity professor) {

		ProfessorEntity prof = new ProfessorEntity();
		
		prof.setNome(professor.getNome());
		prof.setEmail(professor.getEmail());
		prof.setCpf(professor.getCpf());
		prof.setTelefone(professor.getTelefone());

		prof = this.professorRepository.save(prof);

		return prof;
	}
	
	public ProfessorEntity update(int id, ProfessorEntity professor) {
		Optional<ProfessorEntity> profOptional = this.professorRepository.findById(id);
		
		if(profOptional.isPresent()) {
			ProfessorEntity profUpdate = profOptional.get();
			
			profUpdate.setNome(professor.getNome());
			profUpdate.setEmail(professor.getEmail());
			profUpdate.setCpf(professor.getCpf());
			profUpdate.setTelefone(professor.getTelefone());
			
			return this.professorRepository.save(profUpdate);
		}
		else {
			return null;
		}
	}
	
	public void delete(int id) {
		this.professorRepository.deleteById(id);
		}
	
}
