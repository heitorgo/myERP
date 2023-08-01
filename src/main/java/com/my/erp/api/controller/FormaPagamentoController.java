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

import com.my.erp.api.assembler.formaPagamento.FormaPagamentoInputDisassember;
import com.my.erp.api.assembler.formaPagamento.FormaPagamentoModelAssembler;
import com.my.erp.api.model.formaPagamento.FormaPagamentoModel;
import com.my.erp.api.model.input.formaPagamento.FormaPagamentoInput;
import com.my.erp.domain.exception.FormaPagamentoNaoEncontradaException;
import com.my.erp.domain.model.FormaPagamento;
import com.my.erp.domain.repository.FormaPagamentoRepository;
import com.my.erp.domain.service.FormaPagamentoService;



@RequestMapping("/formas-pagamento")
@RestController
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassember formaPagamentoInputDisassembler;

	@GetMapping
	public List<FormaPagamentoModel> listar() {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
		return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
	}

	@GetMapping("/{id}")
	public FormaPagamentoModel buscar(@PathVariable Long id) {
		return formaPagamentoModelAssembler.toModel(formaPagamentoService.buscarOuFalhar(id));
	}

	@GetMapping("/titulo")
	public List<FormaPagamentoModel> listarPorTitulo(String titulo) {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAllByTituloContaining(titulo);
		if (formasPagamento.isEmpty()) {
			throw new FormaPagamentoNaoEncontradaException(String.format("Nenhuma forma de pagamento contém o titulo %s", titulo));
		}
		return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainModel(formaPagamentoInput);
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}

	@PutMapping("/{id}")
	public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoAlteracaoInput) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoAlteracaoInput, formaPagamentoAtual);
		formaPagamentoAtual = formaPagamentoService.salvar(formaPagamentoAtual);
		return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		formaPagamentoService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		formaPagamentoService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		formaPagamentoService.inativar(id);
	}

}
