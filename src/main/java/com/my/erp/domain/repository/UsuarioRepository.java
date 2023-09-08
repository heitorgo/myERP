package com.my.erp.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.my.erp.domain.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	List<Usuario> findAllByNomeContaining(String nome);
	
	@Query("from Usuario where email = :email")
	UserDetails findByEmail_User(@Param("email") String email);
	
	Optional<Usuario> findByEmail(String email);

}
