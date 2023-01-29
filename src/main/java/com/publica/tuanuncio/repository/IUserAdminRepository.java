package com.publica.tuanuncio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.publica.tuanuncio.model.Usuario;

@Repository
public interface IUserAdminRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
	
	 Optional<Usuario> findByUsername(String username);
	 boolean existsByUsername(String username);
	 boolean existsByEmail(String email);
}
