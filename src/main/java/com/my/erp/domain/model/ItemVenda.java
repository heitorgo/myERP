package com.my.erp.domain.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private BigDecimal valorTotal;
	
	private BigDecimal valorUnitario;
	
	private Integer quantidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Venda venda;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;
	
	public void calcularValorTotal() {
		BigDecimal valorTotal = this.getValorTotal();
		Integer quantidade = this.getQuantidade();
		
		if(valorTotal == null) {
			valorTotal = BigDecimal.ZERO;
		}
		if(quantidade == null) {
			quantidade = 0;
		}
		
		this.setValorTotal(valorUnitario.multiply(BigDecimal.valueOf(quantidade)));
	}
	
}
