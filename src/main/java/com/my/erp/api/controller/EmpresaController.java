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

import com.my.erp.api.assembler.empresa.EmpresaInputDisassember;
import com.my.erp.api.assembler.empresa.EmpresaModelAssembler;
import com.my.erp.api.model.empresa.EmpresaModel;
import com.my.erp.api.model.input.empresa.EmpresaInput;
import com.my.erp.domain.exception.EmpresaNaoEncontradaException;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.repository.EmpresaRepository;
import com.my.erp.domain.service.EmpresaService;



@RequestMapping("/empresas")
@RestController
public class EmpresaController {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private EmpresaModelAssembler empresaModelAssembler;
	
	@Autowired
	private EmpresaInputDisassember empresaInputDisassember;

	@GetMapping
	public List<EmpresaModel> listar() {
		List<Empresa> empresas = empresaRepository.findAll();
		return empresaModelAssembler.toCollectionModel(empresas);
	}

	@GetMapping("/{id}")
	public EmpresaModel buscar(@PathVariable Long id) {
		Empresa empresa = empresaService.buscarOuFalhar(id);
		return empresaModelAssembler.toModel(empresa);
	}

	@GetMapping("/nome")
	public List<EmpresaModel> listarPorNome(String nome) {
		List<Empresa> empresas = empresaRepository.findAllByNomeContaining(nome);
		if (empresas.isEmpty()) {
			throw new EmpresaNaoEncontradaException(String.format("Nenhuma empresa contém o nome %s", nome));
		}
		return empresaModelAssembler.toCollectionModel(empresas);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmpresaModel adicionar(@RequestBody @Valid EmpresaInput empresaInput) {
		Empresa empresa = empresaInputDisassember.toDomainModel(empresaInput);
		empresa = empresaService.salvar(empresa);
		return empresaModelAssembler.toModel(empresa);
	}

	@PutMapping("/{id}")
	public EmpresaModel atualizar(@PathVariable Long id, @RequestBody @Valid EmpresaInput empresaInput) {
		Empresa empresaAtual = empresaService.buscarOuFalhar(id);
		empresaInputDisassember.copyToDomainObject(empresaInput, empresaAtual);
		empresaAtual = empresaService.salvar(empresaAtual);
		return empresaModelAssembler.toModel(empresaAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		empresaService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		empresaService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		empresaService.inativar(id);
	}

}
