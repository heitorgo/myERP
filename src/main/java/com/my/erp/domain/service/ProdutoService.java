package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.exception.ProdutoNaoEncontradoException;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.model.Produto;
import com.my.erp.domain.repository.ProdutoRepository;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private LojaService lojaService;

	private final static String msg_produto_em_uso = "Produto de codigo identificador %d está em uso";
	
	private final static String msg_produto_inativo = "Produto de codigo identificador %d está inativo";

	@Transactional
	public Produto salvar(Produto produto) {
		Long lojaId = produto.getLoja().getId();
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		lojaService.verificarAtivo(loja);
		produto.setLoja(loja);
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public Produto salvar(Produto produto, Long lojaId) {
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		lojaService.verificarAtivo(loja);
		produto.setLoja(loja);
		return produtoRepository.save(produto);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			produtoRepository.deleteById(id);
			produtoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_produto_em_uso, id));
		}
	}

	public Produto buscarOuFalhar(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}
	
	public Produto buscarOuFalhar(Long lojaId, Long produtoId) {
		return produtoRepository.findById(lojaId, produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, lojaId));
	}
	
	public void verificarAtivo(Produto produto) {
		if(!produto.getAtivo()) {
			throw new NegocioException(String.format(msg_produto_inativo, produto.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Produto produtoAtual = buscarOuFalhar(id);
		produtoAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		Produto produtoAtual = buscarOuFalhar(id);
		produtoAtual.inativar();
	}
	
	@Transactional
	public void ativar(Long lojaId, Long produtoId) {
		Produto produtoAtual = buscarOuFalhar(lojaId, produtoId);
		produtoAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long lojaId, Long produtoId) {
		Produto produtoAtual = buscarOuFalhar(lojaId, produtoId);
		produtoAtual.inativar();
	}
	
	@Transactional
	public void reduzirQuantidade(Produto produto, Integer quantidadeReduzida) {
		if(produto.getQuantidade() < quantidadeReduzida) {
			throw new NegocioException(String.format(
					"A quantidade em estoque do produto de código %d é menor que a quantidade a ser reduzida", produto.getId()));
		}
		produto.reduzirQuantidade(quantidadeReduzida);
	}

}
