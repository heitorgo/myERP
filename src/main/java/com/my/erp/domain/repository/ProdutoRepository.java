package com.my.erp.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Loja;
import com.my.erp.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

	List<Produto> findAll();
	
	List<Produto> findAllByNomeContaining(String nome);
	
	List<Produto> findByLoja(Loja loja);
	
	@Query("from Produto where loja.id = :loja and id = :produto")
	Optional<Produto> findById(@Param("loja") Long lojaId, @Param("produto") Long produtoId);

}
