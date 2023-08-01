package com.my.erp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Permissao;


@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	
	List<Permissao> findAllByNomeContaining(String nome);

}
