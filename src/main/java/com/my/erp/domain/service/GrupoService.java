package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.GrupoNaoEncontradoException;
import com.my.erp.domain.model.Grupo;
import com.my.erp.domain.model.Permissao;
import com.my.erp.domain.repository.GrupoRepository;


@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;

	private static final String msg_grupo_em_uso = "O grupo de codigo identificador %d está em uso";

	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_grupo_em_uso, id));
		}
	}

	public Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		grupo.associarPermissao(permissao);
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		grupo.desassociarPermissao(permissao);
	}
}
