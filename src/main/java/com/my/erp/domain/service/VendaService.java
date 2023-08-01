package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.VendaNaoEncontradaException;
import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.FormaPagamento;
import com.my.erp.domain.model.Funcionario;
import com.my.erp.domain.model.Produto;
import com.my.erp.domain.model.Venda;
import com.my.erp.domain.repository.VendaRepository;


@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private CaixaService caixaService;

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private ProdutoService produtoService;

	private static final String msg_venda_em_uso = "A venda de codigo identificador %d está em uso";

	@Transactional
	public Venda salvar(Venda venda) {
		validarVenda(venda);
		validarItens(venda);
		venda.calcularValor();
		return vendaRepository.save(venda);
	}
	
	@Transactional
	public Venda salvar(Venda venda, Long lojaId, Long caixaId) {
		validarVenda(venda, lojaId, caixaId);
		validarItens(venda);
		venda.calcularValor();
		return vendaRepository.save(venda);
	}
	
	private void validarVenda(Venda venda) {
		Long caixaId = venda.getCaixa().getId();
		Caixa caixa = caixaService.buscarOuFalhar(caixaId);
		caixaService.verificarAtivo(caixa);
		venda.setCaixa(caixa);
		Long formaPagamentoId = venda.getFormaPagamento().getId();
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		formaPagamentoService.verificarAtivo(formaPagamento);
		venda.setFormaPagamento(formaPagamento);
		//verificação necessaria pois o funcionario não é obrigatório na venda
		if (venda.getFuncionario() != null) {
			Long funcionarioId = venda.getFuncionario().getId();
			Funcionario funcionario = funcionarioService.buscarLojaOuFalhar(caixa.getLoja().getId(), funcionarioId);
			funcionarioService.verificarAtivo(funcionario);
			venda.setFuncionario(funcionario);
		}
		
	}
	
	private void validarVenda(Venda venda, Long lojaId, Long caixaId) {
		Caixa caixa = caixaService.buscarOuFalhar(lojaId, caixaId);
		caixaService.verificarAtivo(caixa);
		venda.setCaixa(caixa);
		Long formaPagamentoId = venda.getFormaPagamento().getId();
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		formaPagamentoService.verificarAtivo(formaPagamento);
		venda.setFormaPagamento(formaPagamento);
		//verificação necessaria pois o funcionario não é obrigatório na venda
		if (venda.getFuncionario() != null) {
			Long funcionarioId = venda.getFuncionario().getId();
			Funcionario funcionario = funcionarioService.buscarLojaOuFalhar(lojaId, funcionarioId);
			funcionarioService.verificarAtivo(funcionario);
			venda.setFuncionario(funcionario);
		}
		
	}
	
	private void validarItens(Venda venda) {
		venda.getItens().forEach(item -> {
			Produto produto = produtoService.buscarOuFalhar(
				venda.getCaixa().getLoja().getId(), item.getProduto().getId());
			produtoService.reduzirQuantidade(produto, item.getQuantidade());
			item.setVenda(venda);
			item.setProduto(produto);
			item.setValorUnitario(produto.getValor());
		});
	}

	@Transactional
	public void excluir(Long id) {
		try {
			vendaRepository.deleteById(id);
			vendaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new VendaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_venda_em_uso, id));
		}
	}

	public Venda buscarOuFalhar(Long id) {
		return vendaRepository.findById(id).orElseThrow(() -> new VendaNaoEncontradaException(id));
	}
	
	public Venda buscarOuFalhar(Long caixaId, Long vendaId) {
		return vendaRepository.findById(caixaId, vendaId).orElseThrow(() -> new VendaNaoEncontradaException(vendaId, caixaId));
	}

}
