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

import com.my.erp.api.assembler.caixa.CaixaInputDisassembler;
import com.my.erp.api.assembler.caixa.CaixaModelAssembler;
import com.my.erp.api.model.caixa.CaixaModel;
import com.my.erp.api.model.caixa.CaixaResumoModel;
import com.my.erp.api.model.input.caixa.CaixaInput;
import com.my.erp.domain.exception.CaixaNaoEncontradoException;
import com.my.erp.domain.exception.LojaNaoEncontradaException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.repository.CaixaRepository;
import com.my.erp.domain.service.CaixaService;

@RequestMapping("/caixas")
@RestController
public class CaixaController {

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private CaixaModelAssembler caixaModelAssembler;
	
	@Autowired
	private CaixaInputDisassembler caixaInputDisassembler;

	@GetMapping
	public List<CaixaResumoModel> listar() {
		List<Caixa> caixas = caixaRepository.findAll();
		return caixaModelAssembler.toCollectionResumoModel(caixas);
	}

	@GetMapping("/{id}")
	public CaixaModel buscar(@PathVariable Long id) {
		Caixa caixa = caixaService.buscarOuFalhar(id);
		return caixaModelAssembler.toModel(caixa);
	}

	@GetMapping("/nome")
	public List<CaixaModel> listarPorNome(String nome) {
		List<Caixa> caixas = caixaRepository.findAllByNomeContaining(nome);
		if (caixas.isEmpty()) {
			throw new CaixaNaoEncontradoException(String.format("Nenhuma caixa contém o nome %s", nome));
		}
		return caixaModelAssembler.toCollectionModel(caixas);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CaixaModel adicionar(@RequestBody @Valid CaixaInput caixaInput) {
		try {
			Caixa caixa = caixaInputDisassembler.toDomainModel(caixaInput);
			caixa = caixaService.salvar(caixa);
			return caixaModelAssembler.toModel(caixa);
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public CaixaModel atualizar(@PathVariable Long id, @RequestBody @Valid CaixaInput caixaInput) {
		try {
			Caixa caixaAtual = caixaService.buscarOuFalhar(id);
			caixaInputDisassembler.copyToDomainObject(caixaInput, caixaAtual);
			caixaAtual = caixaService.salvar(caixaAtual);
			return caixaModelAssembler.toModel(caixaAtual);
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		caixaService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		caixaService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		caixaService.inativar(id);
	}
	
	@GetMapping("/{id}/saldo")
	public BigDecimal exibirSaldo(@PathVariable Long id) {
		return caixaService.calcularValorVendas(id);
	}

}
