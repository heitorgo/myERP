package com.my.erp.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String razaoSocial;
	
	@ManyToMany
	@JoinTable(name = "empresa_usuario_responsavel", joinColumns = @JoinColumn(name = "empresa_id"),
					inverseJoinColumns = @JoinColumn(name="usuario_id"))
	private Set<Usuario> usuarios = new HashSet<>();

	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;

	@OneToMany(mappedBy = "empresa")
	private List<Loja> lojas = new ArrayList<>();
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public boolean associarResponsavel(Usuario usuario) {
		return getUsuarios().add(usuario);
	}
	
	public boolean desassociarResponsavel(Usuario usuario) {
		return getUsuarios().remove(usuario);
	}

}
