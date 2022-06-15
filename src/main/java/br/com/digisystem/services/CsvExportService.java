package br.com.digisystem.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.repositories.ProfessorRepository;

@Service
public class CsvExportService {

    private static final Logger log = getLogger(CsvExportService.class);

    private final ProfessorRepository professorRepository;

    public CsvExportService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void writeEmployeesToCsv(Writer writer) {

        List<ProfessorEntity> professores = professorRepository.findAll();
        
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
        	
        	csvPrinter.printRecord();
            for (ProfessorEntity prof : professores) {
                csvPrinter.printRecord(
                		prof.getId(), prof.getNome(), prof.getCpf(),
                		prof.getEmail(), prof.getTelefone());
            }
        } catch (IOException e) {
            log.error("Erro ao gerar CSV ", e);
        }
    }
}
