package com.my.erp.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EmpresaNaoEncontradaException;
import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Usuario;
import com.my.erp.domain.repository.EmpresaRepository;


@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EntityManager manager;

	private static final String msg_empresa_em_uso = "Empresa de codigo identificador %d está em uso";
	private static final String msg_empresa_inativa = "Empresa de codigo identificador %d está inativa";

	@Transactional
	public Empresa salvar(Empresa empresa) {
		manager.detach(empresa);
		Optional<Empresa> empresaExistente = empresaRepository.findByRazaoSocial(empresa.getRazaoSocial());
		if(empresaExistente.isPresent() && !empresaExistente.get().equals(empresa)) {
			throw new NegocioException(
					String.format("Já existe uma empresa cadastrada com o a razao social %s", empresa.getRazaoSocial()));
		}
		return empresaRepository.save(empresa);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			empresaRepository.deleteById(id);
			empresaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EmpresaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_empresa_em_uso, id));
		}
	}

	public Empresa buscarOuFalhar(Long id) {
		return empresaRepository.findById(id).orElseThrow(() -> new EmpresaNaoEncontradaException(id));
	}
	
	public void verificarAtivo(Empresa empresa) {
		if(!empresa.getAtivo()) {
			throw new NegocioException(String.format(msg_empresa_inativa, empresa.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long empresaId) {
		Empresa empresaAtual = buscarOuFalhar(empresaId);
		empresaAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long empresaId) {
		Empresa empresaAtual = buscarOuFalhar(empresaId);
		empresaAtual.inativar();
	}
	
	@Transactional
	public void associarResponsavel(Long empresaId, Long usuarioId) {
		Empresa empresa = buscarOuFalhar(empresaId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		empresa.associarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarResponsavel(Long empresaId, Long usuarioId) {
		Empresa empresa = buscarOuFalhar(empresaId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		empresa.desassociarResponsavel(usuario);
	}

}
