package com.my.erp.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id"),
				inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private Set<Permissao> permissoes = new HashSet<>();
	
	public boolean desassociarPermissao(Permissao permissao) {
		return getPermissoes().remove(permissao);
	}
	
	public boolean associarPermissao(Permissao permissao) {
		return getPermissoes().add(permissao);
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

}
