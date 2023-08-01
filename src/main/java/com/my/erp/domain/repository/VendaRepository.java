package com.my.erp.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda> {

	@Query("from Venda v join fetch v.caixa join fetch v.funcionario ")
	List<Venda> findAll();
	
	List<Venda> findByCaixa(Caixa caixa);

	List<Venda> findAllByDescricaoContaining(String descricao);
	
	@Query("from Venda where caixa.id = :caixa and id = :venda")
	Optional<Venda> findById(@Param("caixa") Long caixaId, @Param("venda") Long vendaId);

}
