package com.my.erp.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Loja;


public interface CargoRepository extends JpaRepository<Cargo, Long> {
	
	@Query("from Cargo c join fetch c.loja l join fetch l.empresa")
	List<Cargo> findAll();
	
	List<Cargo> findByLoja(Loja loja);
	
	List<Cargo> findAllByTituloContaining(String titulo);

	@Query("from Cargo where loja.id = :loja and id = :cargo")
	Optional<Cargo> findById(@Param("loja") Long lojaId, @Param("cargo") Long cargoId);
}
