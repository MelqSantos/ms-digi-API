package br.com.digisystem.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import br.com.digisystem.dtos.ProfessorDTO;
import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.services.CsvExportService;
import br.com.digisystem.services.ProfessorService;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	@Autowired
	private ProfessorService profService;
	
	@Autowired
	private CsvExportService csvExportService;
	
	@GetMapping
	public ResponseEntity<List<ProfessorDTO>> getAll(){
		
		List<ProfessorEntity> lista = this.profService.getAll();
		
		List<ProfessorDTO> listaDTO = new ArrayList<>();
		
		// Converte cada elemento para DTO
		for(int i = 0; i < lista.size(); i++) {
			listaDTO.add(lista.get(i).toDTO());
		}
		
//		// Segundo forma para converter para DTO
//		List<ProfessorDTO> listaDTO2 = lista.stream().map(x -> x.toDTO())
//				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProfessorDTO> getById(@PathVariable int id) {
		
		return ResponseEntity.ok().body(this.profService.getOne(id).toDTO());
	}
	
	@PostMapping
	public ResponseEntity<ProfessorDTO> create(@RequestBody ProfessorDTO prof) {
		
		ProfessorEntity profEntity = prof.toEntity();
		
		return ResponseEntity.ok().body(this.profService.create(profEntity).toDTO());
	}
	
	@PatchMapping("/{id}")
		public ResponseEntity<ProfessorDTO> update(@PathVariable int id, @RequestBody ProfessorDTO prof) {
		
			ProfessorEntity profEntitySalvo = this.profService.update(id, prof.toEntity());
			
			return ResponseEntity.ok().body(profEntitySalvo.toDTO());
		}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		this.profService.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
	
	@RequestMapping("/export")
    public void getAllProfsInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setHeader("Content-Type", "text/csv; charset=UTF-16LE");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"professores.csv\"");
        csvExportService.writeEmployeesToCsv(servletResponse.getWriter());
    }
	
}
