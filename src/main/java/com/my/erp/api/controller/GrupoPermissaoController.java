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

import com.my.erp.api.assembler.permissao.PermissaoModelAssembler;
import com.my.erp.api.model.permissao.PermissaoModel;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.exception.PermissaoNaoEncontradaException;
import com.my.erp.domain.model.Grupo;
import com.my.erp.domain.service.GrupoService;



@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@GetMapping
	public List<PermissaoModel> listarPermissoes(@PathVariable Long grupoId){
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes()); 
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		try {
			grupoService.associarPermissao(grupoId, permissaoId);
		}catch(PermissaoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		try {
			grupoService.desassociarPermissao(grupoId, permissaoId);
		}catch(PermissaoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
