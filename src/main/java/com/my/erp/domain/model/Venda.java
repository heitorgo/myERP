package com.my.erp.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private BigDecimal valor;

	@Column(nullable = false)
	private String descricao;

	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Caixa caixa;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
	private List<ItemVenda> itens = new ArrayList<>();
	
	public void calcularValor() {
		getItens().forEach(ItemVenda::calcularValorTotal);
		
		this.valor = getItens().stream()
				.map(item -> item.getValorTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void atribuirVendaAosItens() {
		getItens().forEach(item -> item.setVenda(this));
	}

}
