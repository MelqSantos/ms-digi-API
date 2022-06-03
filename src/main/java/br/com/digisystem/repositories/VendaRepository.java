package br.com.digisystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.digisystem.entities.VendaEntity;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Integer> {

}
