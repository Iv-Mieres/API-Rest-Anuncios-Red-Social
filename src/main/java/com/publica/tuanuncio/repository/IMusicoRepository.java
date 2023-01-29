package com.publica.tuanuncio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.publica.tuanuncio.model.Musico;

@Repository
public interface IMusicoRepository extends JpaRepository<Musico, Long> {
}
