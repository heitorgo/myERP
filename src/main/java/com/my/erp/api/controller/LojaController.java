package com.my.erp.api.controller;

import java.math.BigDecimal;
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

import com.my.erp.api.assembler.loja.LojaInputDisassembler;
import com.my.erp.api.assembler.loja.LojaModelAssembler;
import com.my.erp.api.model.input.loja.LojaInput;
import com.my.erp.api.model.loja.LojaModel;
import com.my.erp.domain.exception.EmpresaNaoEncontradaException;
import com.my.erp.domain.exception.LojaNaoEncontradaException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.repository.LojaRepository;
import com.my.erp.domain.service.LojaService;

@RequestMapping("/lojas")
@RestController
public class LojaController {

	@Autowired
	private LojaRepository lojaRepository;

	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private LojaModelAssembler lojaModelAssembler;
	
	@Autowired
	private LojaInputDisassembler lojaInputDisassembler;

	@GetMapping
	public List<LojaModel> listar() {
		List<Loja> lojas = lojaRepository.findAll();
		return lojaModelAssembler.toCollectionModel(lojas);
	}

	@GetMapping("/{id}")
	public LojaModel buscar(@PathVariable Long id) {
		Loja loja = lojaService.buscarOuFalhar(id);
		return lojaModelAssembler.toModel(loja);
	}

	@GetMapping("/nome")
	public List<LojaModel> listarPorNome(String nome) {
		List<Loja> lojas = lojaRepository.findAllByNomeContaining(nome);
		if (lojas.isEmpty()) {
			throw new LojaNaoEncontradaException(String.format("Nenhuma loja contém o nome %s", nome));
		}
		return lojaModelAssembler.toCollectionModel(lojas);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LojaModel adicionar(@RequestBody @Valid LojaInput lojaInput) {
		try {
			Loja loja = lojaInputDisassembler.toDomainModel(lojaInput);
			loja = lojaService.salvar(loja);
			return lojaModelAssembler.toModel(loja);
		} catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public LojaModel atualizar(@PathVariable Long id, @RequestBody @Valid LojaInput lojaInput) {
		try {
			Loja lojaAtual = lojaService.buscarOuFalhar(id);
			lojaInputDisassembler.copyToDomainObject(lojaInput, lojaAtual);
			lojaAtual = lojaService.salvar(lojaAtual);
			return lojaModelAssembler.toModel(lojaAtual);
		} catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		lojaService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		lojaService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		lojaService.inativar(id);
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		lojaService.abrir(id);
	}
	
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		lojaService.fechar(id);
	}
	
	@GetMapping("/{id}/saldo")
	public BigDecimal exibirSaldo(@PathVariable Long id) {
		return lojaService.calcularSaldo(id);
	}

}
