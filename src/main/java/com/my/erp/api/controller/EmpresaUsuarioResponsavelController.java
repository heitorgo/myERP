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

import com.my.erp.api.assembler.usuario.UsuarioModelAssembler;
import com.my.erp.api.model.usuario.UsuarioModel;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.exception.UsuarioNaoEncontradoException;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.service.EmpresaService;



@RestController
@RequestMapping(value = "/empresas/{empresaId}/responsaveis")
public class EmpresaUsuarioResponsavelController {
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public List<UsuarioModel> listarResponsaveis(@PathVariable Long empresaId){
		Empresa empresa = empresaService.buscarOuFalhar(empresaId);
		return usuarioModelAssembler.toCollectionModel(empresa.getUsuarios()); 
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarUsuario(@PathVariable Long empresaId, @PathVariable Long usuarioId) {
		try {
			empresaService.associarResponsavel(empresaId, usuarioId);
		}catch(UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarUsuario(@PathVariable Long empresaId, @PathVariable Long usuarioId) {
		try {
			empresaService.desassociarResponsavel(empresaId, usuarioId);
		}catch(UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
