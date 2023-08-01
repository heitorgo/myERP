package com.my.erp.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.erp.api.assembler.venda.VendaInputDisassembler;
import com.my.erp.api.assembler.venda.VendaModelAssembler;
import com.my.erp.api.model.input.venda.VendaInput;
import com.my.erp.api.model.venda.VendaModel;
import com.my.erp.api.model.venda.VendaResumoModel;
import com.my.erp.domain.exception.CaixaNaoEncontradoException;
import com.my.erp.domain.exception.FuncionarioNaoEncontradoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.exception.VendaNaoEncontradaException;
import com.my.erp.domain.model.Venda;
import com.my.erp.domain.repository.VendaRepository;
import com.my.erp.domain.service.VendaService;

@RequestMapping("/vendas")
@RestController
public class VendaController {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private VendaModelAssembler vendaModelAssembler;
	
	@Autowired
	private VendaInputDisassembler vendaInputDisassembler;

	@GetMapping
	public List<VendaResumoModel> listar() {
		List<Venda> vendas = vendaRepository.findAll();
		return vendaModelAssembler.toCollectionResumoModel(vendas);
	}

	@GetMapping("/{id}")
	public VendaModel buscar(@PathVariable Long id) {
		return vendaModelAssembler.toModel(vendaService.buscarOuFalhar(id));
	}

	@GetMapping("/descricao")
	public List<VendaModel> listarPorDescricao(String descricao) {
		List<Venda> vendas = vendaRepository.findAllByDescricaoContaining(descricao);
		if (vendas.isEmpty()) {
			throw new VendaNaoEncontradaException(String.format("Nenhuma venda contém a descrição %s", descricao));
		}
		return vendaModelAssembler.toCollectionModel(vendas);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendaModel adicionar(@RequestBody @Valid VendaInput vendaInput) {
		try {
			Venda venda = vendaInputDisassembler.toDomainModel(vendaInput);
			venda = vendaService.salvar(venda);
			return vendaModelAssembler.toModel(venda);
		} catch (CaixaNaoEncontradoException | FuncionarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		vendaService.excluir(id);
	}

}