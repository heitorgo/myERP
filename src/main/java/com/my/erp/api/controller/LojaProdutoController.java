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

import com.my.erp.api.assembler.produto.ProdutoInputDisassembler;
import com.my.erp.api.assembler.produto.ProdutoModelAssembler;
import com.my.erp.api.model.input.produto.ProdutoInput;
import com.my.erp.api.model.produto.LojaProdutoModel;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.model.Produto;
import com.my.erp.domain.repository.ProdutoRepository;
import com.my.erp.domain.service.LojaService;
import com.my.erp.domain.service.ProdutoService;

@RequestMapping("lojas/{lojaId}/produtos")
@RestController
public class LojaProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private LojaService lojaService;

	@GetMapping
	public List<LojaProdutoModel> listar(@PathVariable Long lojaId) {
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		
		List<Produto> produtos = produtoRepository.findByLoja(loja);
		return produtoModelAssembler.toCollectionLojaModel(produtos);
	}

	@GetMapping("/{produtoId}")
	public LojaProdutoModel buscar(@PathVariable Long lojaId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscarOuFalhar(lojaId, produtoId);
		return produtoModelAssembler.toLojaModel(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LojaProdutoModel adicionar(@PathVariable Long lojaId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = produtoInputDisassembler.toDomainModel(produtoInput);
		produto = produtoService.salvar(produto, lojaId);
		return produtoModelAssembler.toLojaModel(produto);
	}

	@PutMapping("/{produtoId}")
	public LojaProdutoModel atualizar(@PathVariable Long lojaId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = produtoService.buscarOuFalhar(lojaId, produtoId);
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		produtoAtual = produtoService.salvar(produtoAtual);
		return produtoModelAssembler.toLojaModel(produtoAtual);

	}
	
	@PutMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long lojaId, @PathVariable Long produtoId) {
		produtoService.ativar(lojaId, produtoId);
	}
	
	@DeleteMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long lojaId, @PathVariable Long produtoId) {
		produtoService.inativar(lojaId, produtoId);
	}

}
