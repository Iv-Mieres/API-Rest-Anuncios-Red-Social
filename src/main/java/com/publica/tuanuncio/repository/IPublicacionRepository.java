package com.publica.tuanuncio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.publica.tuanuncio.model.Publicacion;

@Repository
public interface IPublicacionRepository extends JpaRepository<Publicacion, Long>, JpaSpecificationExecutor<Publicacion> {
}
