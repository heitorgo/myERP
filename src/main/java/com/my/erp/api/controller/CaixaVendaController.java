package com.my.erp.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.erp.api.assembler.venda.VendaInputDisassembler;
import com.my.erp.api.assembler.venda.VendaModelAssembler;
import com.my.erp.api.model.input.venda.CaixaVendaInput;
import com.my.erp.api.model.venda.CaixaVendaModel;
import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Venda;
import com.my.erp.domain.repository.VendaRepository;
import com.my.erp.domain.service.CaixaService;
import com.my.erp.domain.service.VendaService;


@RestController
@RequestMapping(value = "/lojas/{lojaId}/caixas/{caixaId}/vendas")
public class CaixaVendaController {
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private VendaModelAssembler vendaModelAssembler;
	
	@Autowired
	private VendaInputDisassembler vendaInputDisassembler;
	
	@GetMapping
	public List<CaixaVendaModel> listar(@PathVariable Long lojaId, @PathVariable Long caixaId){
		Caixa caixa = caixaService.buscarOuFalhar(lojaId, caixaId);
		List<Venda> vendas = vendaRepository.findByCaixa(caixa);
		return vendaModelAssembler.toCollectionCaixaModel(vendas); 
	}
	
	@GetMapping("/{vendaId}")
	public CaixaVendaModel buscar(@PathVariable Long lojaId, @PathVariable Long caixaId, @PathVariable Long vendaId) {
		Caixa caixa = caixaService.buscarOuFalhar(lojaId, caixaId);
		Venda venda = vendaService.buscarOuFalhar(caixa.getId(), vendaId);
		return vendaModelAssembler.toCaixaModel(venda);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CaixaVendaModel salvar(@PathVariable Long lojaId, @PathVariable Long caixaId, @RequestBody @Valid CaixaVendaInput caixaVendaInput) {
		Venda venda = vendaInputDisassembler.toDomainModel(caixaVendaInput);
		venda = vendaService.salvar(venda, lojaId, caixaId);
		return vendaModelAssembler.toCaixaModel(venda);
	}

}
