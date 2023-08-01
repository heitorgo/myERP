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

import com.my.erp.api.assembler.grupo.GrupoInputDisassembler;
import com.my.erp.api.assembler.grupo.GrupoModelAssembler;
import com.my.erp.api.model.grupo.GrupoModel;
import com.my.erp.api.model.input.grupo.GrupoInput;
import com.my.erp.domain.exception.GrupoNaoEncontradoException;
import com.my.erp.domain.model.Grupo;
import com.my.erp.domain.repository.GrupoRepository;
import com.my.erp.domain.service.GrupoService;



@RequestMapping("/grupos")
@RestController
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassember;

	@GetMapping
	public List<GrupoModel> listar() {
		List<Grupo> grupos = grupoRepository.findAll();
		return grupoModelAssembler.toCollectionModel(grupos);
	}

	@GetMapping("/{id}")
	public GrupoModel buscar(@PathVariable Long id) {
		Grupo grupo = grupoService.buscarOuFalhar(id);
		return grupoModelAssembler.toModel(grupo);
	}

	@GetMapping("/nome")
	public List<GrupoModel> listarPorNome(String nome) {
		List<Grupo> grupos = grupoRepository.findAllByNomeContaining(nome);
		if (grupos.isEmpty()) {
			throw new GrupoNaoEncontradoException(String.format("Nenhuma permissao contém o nome %s", nome));
		}
		return grupoModelAssembler.toCollectionModel(grupos);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassember.toDomainModel(grupoInput);
		grupo = grupoService.salvar(grupo);
		return grupoModelAssembler.toModel(grupo);
	}

	@PutMapping("/{id}")
	public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(id);
		grupoInputDisassember.copyToDomainObject(grupoInput, grupoAtual);
		grupoAtual = grupoService.salvar(grupoAtual);
		return grupoModelAssembler.toModel(grupoAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		grupoService.excluir(id);
	}
	
}
