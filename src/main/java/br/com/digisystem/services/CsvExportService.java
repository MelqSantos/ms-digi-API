package br.com.digisystem.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.EnderecoEntity;
import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.repositories.ProfessorRepository;
import br.com.digisystem.repositories.UsuarioReposistory;

@Service
public class CsvExportService {

    private static final Logger log = getLogger(CsvExportService.class);

    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private UsuarioReposistory usuarioReposistory;
    
    // Professores
    public void writeProfessoresToCsv(Writer writer) {

        List<ProfessorEntity> professores = professorRepository.findAll();
        
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (ProfessorEntity prof : professores) {
                csvPrinter.printRecord(
                		prof.getId(), prof.getNome(), prof.getCpf(),
                		prof.getEmail(), prof.getTelefone());
            }
        } catch (IOException e) {
            log.error("Erro ao gerar CSV ", e);
        }
    }
    
    // Usuários
    public void writeUsersToCsv(Writer writer) {
    	
        List<UsuarioEntity> usuarios = usuarioReposistory.findAll();
        String[] header = {
        		"User ID", "Nome", "E-mail",
        		"Endereço ID", "Logradouro", "Número", "Complemento", "CEP", "UF"
        		}; 
        
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
        		.withHeader(header))) {
        	
        	    for (UsuarioEntity user : usuarios) {
        	    	
        	    	EnderecoEntity address = user.getEndereco();
        	    	
        			if(address != null) {
	        	    	csvPrinter.printRecord(
	                		user.getId(), user.getNome(), user.getEmail(),
	                		address.getId(),  address.getLogradouro(),
			    			address.getNumero(), address.getComplemento(),
			    			address.getCep(), address.getUf());
        			} 
        			else {
        				csvPrinter.printRecord(
            					user.getId(), user.getNome(), user.getEmail());
        			}
            }
        } catch (IOException e) {
            log.error("Erro ao gerar CSV ", e);
        }
    }
}
