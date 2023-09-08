package com.my.erp.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.erp.api.assembler.usuario.UsuarioInputDisassember;
import com.my.erp.api.assembler.usuario.UsuarioModelAssembler;
import com.my.erp.api.model.input.usuario.SenhaInput;
import com.my.erp.api.model.input.usuario.UsuarioComSenhaInput;
import com.my.erp.api.model.input.usuario.UsuarioInput;
import com.my.erp.api.model.usuario.UsuarioModel;
import com.my.erp.domain.model.Usuario;
import com.my.erp.domain.repository.UsuarioRepository;
import com.my.erp.domain.service.UsuarioService;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassember usuarioInputDisassember;

	@GetMapping
	public List<UsuarioModel> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}

	@GetMapping("/{id}")
	public UsuarioModel buscar(@PathVariable Long id) {
		Usuario usuario = usuarioService.buscarOuFalhar(id);
		return usuarioModelAssembler.toModel(usuario);
	}
	
	// Adicionar encriptação Bcrypt
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
		Usuario usuario = usuarioInputDisassember.toDomainModel(usuarioComSenhaInput);
		usuario = usuarioService.salvar(usuario);
		return usuarioModelAssembler.toModel(usuario);
	}

	@PutMapping("/{id}")
	public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(id);
		usuarioInputDisassember.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		return usuarioModelAssembler.toModel(usuarioAtual);
	}
	
	@PutMapping("/{id}/senha")
	public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		usuarioService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		usuarioService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		usuarioService.inativar(id);
	}

}
