package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.FormaPagamentoNaoEncontradaException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.FormaPagamento;
import com.my.erp.domain.repository.FormaPagamentoRepository;


@Service
public class FormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	private static final String msg_forma_pagamento_em_uso = "A forma de pagamento de codigo identificador %d está em uso";
	private static final String msg_forma_pagamento_inativa = "A forma de pagamento de codigo identificador %d está inativa";

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_forma_pagamento_em_uso, id));
		}
	}

	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}
	
	public void verificarAtivo(FormaPagamento formaPagamento) {
		if(!formaPagamento.getAtivo()) {
			throw new NegocioException(String.format(msg_forma_pagamento_inativa, formaPagamento.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		FormaPagamento formaPagamentoAtual = buscarOuFalhar(id);
		formaPagamentoAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		FormaPagamento formaPagamentoAtual = buscarOuFalhar(id);
		formaPagamentoAtual.inativar();
	}

}
