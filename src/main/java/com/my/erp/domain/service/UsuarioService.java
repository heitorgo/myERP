package com.my.erp.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.exception.UsuarioNaoEncontradoException;
import com.my.erp.domain.model.Grupo;
import com.my.erp.domain.model.Usuario;
import com.my.erp.domain.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private EntityManager manager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final String msg_usuario_em_uso = "O usuário de codigo identificador %d está em uso";
	private static final String msg_usuario_inativo = "O usuário de codigo identificador %d está inativo";
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		manager.detach(usuario);
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuario cadastrado com o e-mail %s", usuario.getEmail()));
		}
		usuario.setSenha(codificarSenha(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(id);
		if(usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("A senha atual informada não coincide com a senha do usuario");
		}
		usuario.setSenha(codificarSenha(novaSenha));
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_usuario_em_uso, id));
		}
	}
	
	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	public void verificarAtivo(Usuario usuario) {
		if(!usuario.getAtivo()) {
			throw new NegocioException(String.format(msg_usuario_inativo, usuario.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Usuario usuario = buscarOuFalhar(id);
		usuario.ativar();
	}
	
	public String codificarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}
	
	@Transactional
	public void inativar(Long id) {
		Usuario usuario = buscarOuFalhar(id);
		usuario.inativar();
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		usuario.associarGrupo(grupo);
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		usuario.desassociarGrupo(grupo);
	}

}
