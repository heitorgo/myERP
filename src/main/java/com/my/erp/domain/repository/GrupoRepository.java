package com.my.erp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Grupo;


@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	List<Grupo> findAllByNomeContaining(String nome);

}
