package com.my.erp.domain.service;

import java.math.BigDecimal;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.LojaNaoEncontradaException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.repository.LojaRepository;

@Service
public class LojaService {

	@Autowired
	private LojaRepository lojaRepository;

	@Autowired
	private EmpresaService empresaService;

	private static final String msg_loja_em_uso = "Loja de codigo identificador %d está em uso";
	private static final String msg_loja_inativa = "Loja de codigo identificador %d está inativa";

	@Transactional
	public Loja salvar(Loja loja) {
		Long empresaId = loja.getEmpresa().getId();
		Empresa empresa = empresaService.buscarOuFalhar(empresaId);
		empresaService.verificarAtivo(empresa);
		loja.setEmpresa(empresa);
		return lojaRepository.save(loja);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			lojaRepository.deleteById(id);
			lojaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new LojaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_loja_em_uso, id));
		}
	}

	public Loja buscarOuFalhar(Long id) {
		return lojaRepository.findById(id).orElseThrow(() -> new LojaNaoEncontradaException(id));
	}
	
	public void verificarAtivo(Loja loja) {
		if(!loja.getAtivo()) {
			throw new NegocioException(String.format(msg_loja_inativa, loja.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Loja lojaAtual = buscarOuFalhar(id);
		lojaAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		Loja lojaAtual = buscarOuFalhar(id);
		lojaAtual.inativar();
	}
	
	@Transactional
	public void abrir(Long id) {
		Loja lojaAtual = buscarOuFalhar(id);
		lojaAtual.abrir();
	}
	
	@Transactional
	public void fechar(Long id) {
		Loja lojaAtual = buscarOuFalhar(id);
		lojaAtual.fechar();
	}
	
	@Transactional
	public BigDecimal calcularSaldo(Long id) {
		Loja lojaAtual = buscarOuFalhar(id);
		return lojaAtual.calcularSaldo();
	}

}
