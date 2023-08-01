package com.my.erp.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Funcionario;


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario>{
	
	@Query("from Funcionario f join fetch f.cargo")
	List<Funcionario> findAll();

	List<Funcionario> findAllByNomeContaining(String nome);
	
	List<Funcionario> findByCargo(Cargo cargo);
	
	@Query("from Funcionario where cargo.id = :cargo and id = :funcionario")
	Optional<Funcionario> findById(@Param("cargo") Long cargoId, @Param("funcionario") Long funcionarioId);
	
	@Query("from Funcionario where cargo.loja.id = :loja and id = :funcionario")
	Optional<Funcionario> findByLojaId(@Param("loja") Long lojaId, @Param("funcionario") Long funcionarioId);

}
