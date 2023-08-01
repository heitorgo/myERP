package com.my.erp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.erp.domain.model.FormaPagamento;


public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

	List<FormaPagamento> findAllByTituloContaining(String titulo);

}
