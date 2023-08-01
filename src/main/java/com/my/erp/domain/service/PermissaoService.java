package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.PermissaoNaoEncontradaException;
import com.my.erp.domain.model.Permissao;
import com.my.erp.domain.repository.PermissaoRepository;


@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	private static final String msg_permissao_em_uso = "Permissao de codigo identificador %d está em uso";

	@Transactional
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			permissaoRepository.deleteById(id);
			permissaoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PermissaoNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_permissao_em_uso, id));
		}
	}

	public Permissao buscarOuFalhar(Long id) {
		return permissaoRepository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}
	

}
