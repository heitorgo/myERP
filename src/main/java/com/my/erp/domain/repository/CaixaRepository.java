package com.my.erp.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Loja;


@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long>, JpaSpecificationExecutor<Caixa> {
	
	@Query("from Caixa c join fetch c.loja")
	List<Caixa> findAll();
	
	List<Caixa> findByLoja(Loja loja);

	List<Caixa> findAllByNomeContaining(String nome);
	
	@Query("from Caixa where loja.id = :loja and id = :caixa")
	Optional<Caixa> findById(@Param("loja") Long lojaId, @Param("caixa") Long caixaId);

}
