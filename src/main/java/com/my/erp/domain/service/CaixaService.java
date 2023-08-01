package com.my.erp.domain.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.CaixaNaoEncontradoException;
import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.repository.CaixaRepository;


@Service
public class CaixaService {

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private LojaService lojaService;

	private final static String msg_caixa_em_uso = "Caixa de codigo identificador %d está em uso";
	private final static String msg_caixa_inativa = "Caixa de codigo identificador %d está inativa";

	@Transactional
	public Caixa salvar(Caixa caixa) {
		Long lojaId = caixa.getLoja().getId();
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		lojaService.verificarAtivo(loja);
		caixa.setLoja(loja);
		return caixaRepository.save(caixa);
	}
	
	@Transactional
	public Caixa salvar(Caixa caixa, Long lojaId) {
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		lojaService.verificarAtivo(loja);
		caixa.setLoja(loja);
		return caixaRepository.save(caixa);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			caixaRepository.deleteById(id);
			caixaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CaixaNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_caixa_em_uso, id));
		}
	}

	public Caixa buscarOuFalhar(Long id) {
		return caixaRepository.findById(id).orElseThrow(() -> new CaixaNaoEncontradoException(id));
	}
	
	public Caixa buscarOuFalhar(Long lojaId, Long caixaId) {
		return caixaRepository.findById(lojaId, caixaId).orElseThrow(() -> new CaixaNaoEncontradoException(caixaId, lojaId));
	}
	
	public void verificarAtivo(Caixa caixa) {
		if(!caixa.getAtivo()) {
			throw new NegocioException(String.format(msg_caixa_inativa, caixa.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Caixa caixaAtual = buscarOuFalhar(id);
		caixaAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		Caixa caixaAtual = buscarOuFalhar(id);
		caixaAtual.inativar();
	}
	
	@Transactional
	public void ativar(Long lojaId, Long caixaId) {
		Caixa caixaAtual = buscarOuFalhar(lojaId, caixaId);
		caixaAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long lojaId, Long caixaId) {
		Caixa caixaAtual = buscarOuFalhar(lojaId, caixaId);
		caixaAtual.inativar();
	}
	
	@Transactional
	public BigDecimal calcularValorVendas(Long id) {
		Caixa caixaAtual = buscarOuFalhar(id);
		return caixaAtual.calcularSaldo();
	}
	
	@Transactional
	public BigDecimal calcularValorVendas(Caixa caixa) {
		return caixa.calcularSaldo();
	}

}
