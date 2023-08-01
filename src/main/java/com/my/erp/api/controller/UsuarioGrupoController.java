package com.my.erp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.erp.api.assembler.grupo.GrupoModelAssembler;
import com.my.erp.api.model.grupo.GrupoModel;
import com.my.erp.domain.exception.GrupoNaoEncontradoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Usuario;
import com.my.erp.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public List<GrupoModel> listarGrupos(@PathVariable Long usuarioId){
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return grupoModelAssembler.toCollectionModel(usuario.getGrupos()); 
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		try {
			usuarioService.associarGrupo(usuarioId, grupoId);
		}catch(GrupoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		try {
			usuarioService.desassociarGrupo(usuarioId, grupoId);
		}catch(GrupoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
