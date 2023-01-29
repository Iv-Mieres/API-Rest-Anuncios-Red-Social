package com.publica.tuanuncio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.publica.tuanuncio.model.Banda;

@Repository
public interface IBandaRepository extends JpaRepository<Banda, Long> {
}

