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
import com.my.erp.api.model.caixa.LojaCaixaModel;
import com.my.erp.api.model.input.caixa.LojaCaixaInput;
import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.repository.CaixaRepository;
import com.my.erp.domain.service.CaixaService;
import com.my.erp.domain.service.LojaService;



@RestController
@RequestMapping(value = "/lojas/{lojaId}/caixas")
public class LojaCaixaController {
	
	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private CaixaRepository caixaRepository;
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private CaixaModelAssembler caixaModelAssembler;
	
	@Autowired
	private CaixaInputDisassembler caixaInputDisassembler;
	
	@GetMapping
	public List<LojaCaixaModel> listar(@PathVariable Long lojaId){
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		List<Caixa> caixas = caixaRepository.findByLoja(loja);
		return caixaModelAssembler.toCollectionLojaModel(caixas); 
	}
	
	@GetMapping("/{caixaId}")
	public LojaCaixaModel buscar(@PathVariable Long lojaId, @PathVariable Long caixaId) {
		Caixa caixa = caixaService.buscarOuFalhar(lojaId, caixaId);
		return caixaModelAssembler.toLojaModel(caixa);
	}
	
	@GetMapping("/{caixaId}/saldo")
	public BigDecimal exibirSaldo(@PathVariable Long lojaId, @PathVariable Long caixaId) {
		Caixa caixa = caixaService.buscarOuFalhar(lojaId, caixaId);
		return caixaService.calcularValorVendas(caixa);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LojaCaixaModel salvar(@PathVariable Long lojaId, @RequestBody @Valid LojaCaixaInput lojaCaixaInput) {
		Caixa caixa = caixaInputDisassembler.toDomainModel(lojaCaixaInput);
		caixa = caixaService.salvar(caixa, lojaId);
		return caixaModelAssembler.toLojaModel(caixa);
	}
	
	@PutMapping("/{caixaId}")
	public LojaCaixaModel atualizar(@PathVariable Long lojaId, @PathVariable Long caixaId, @RequestBody @Valid LojaCaixaInput lojaCaixaInput) {
		Caixa caixaAtual = caixaService.buscarOuFalhar(lojaId, caixaId);
		caixaInputDisassembler.copyToDomainObject(lojaCaixaInput, caixaAtual);
		caixaAtual = caixaService.salvar(caixaAtual);
		return caixaModelAssembler.toLojaModel(caixaAtual);
	}
	
	@PutMapping("/{caixaId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long lojaId, @PathVariable Long caixaId) {
		caixaService.ativar(lojaId, caixaId);
	}
	
	@DeleteMapping("/{caixaId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long lojaId, @PathVariable Long caixaId) {
		caixaService.inativar(lojaId, caixaId);
	}
}
