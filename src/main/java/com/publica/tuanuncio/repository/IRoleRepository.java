package com.publica.tuanuncio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.publica.tuanuncio.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRoleAuthority(String roleAutho);
	boolean existsByRoleAuthority(String roleAutho);
}
