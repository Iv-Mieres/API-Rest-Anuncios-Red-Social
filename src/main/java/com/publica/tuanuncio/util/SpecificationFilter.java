package com.publica.tuanuncio.util;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.model.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

@Component
public class SpecificationFilter {

    //BUSCA USUARIOS POR ID, USERNAME Y/O EMAIL
    public Specification<Usuario> filrarUsuario(FiltroDTO filtro) {

        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getRole() != null){ //busca usuarios por role
                predicates.add(criteriaBuilder.equal(root.join(Usuario_.roles)
                        .get(Role_.ROLE_AUTHORITY), filtro.getRole()));
            }
            if (filtro.getUsername() != null) { //busca usuarios por username
                predicates.add(criteriaBuilder.equal(root.get(Usuario_.USERNAME), filtro.getUsername()));
            }
            if (filtro.getEmail() != null) { //busca usuarios por email
                predicates.add(criteriaBuilder.equal(root.get(Usuario_.EMAIL), filtro.getEmail()));
            }
            if (filtro.getEliminado() != null) { //busca usuarios por eliminado
                predicates.add(criteriaBuilder.equal(root.get(Usuario_.ELIMINADO), filtro.getEliminado()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    //BUSCA PUBLICACIONES POR FECHA Y/0 GENERO MUSICAL
    public Specification<Publicacion> filtrarPublicaciones(FiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getFechaPublicacion() != null) { //busca publicaciones por fecha
                predicates.add(criteriaBuilder.equal(root.get(Publicacion_.FECHA_PUBLICACION), filtro.getFechaPublicacion()));
            }
            if (filtro.getGeneroMusical() != null) { //busca publicaciones por genero músical
                predicates.add(criteriaBuilder.equal(root.get(Publicacion_.GENERO_MUSICAL), filtro.getGeneroMusical()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    //BUSCA PUBLICACIONES POR ATRIBUTOS DE BANDA (PROVINCIA Y/ LOCALIDAD)
    public Specification<Publicacion> filtrarPublicacionPorBanda(FiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getProvincia() != null) { //busca publicaciones por provincia de usuarios banda
                predicates.add(criteriaBuilder.equal(root.join(Publicacion_.banda)
                        .get(Banda_.PROVINCIA), filtro.getProvincia()));
            }
            if (filtro.getLocalidad() != null) { //busca publicaciones por localidad de usuarios banda
                predicates.add(criteriaBuilder.equal(root.join(Publicacion_.banda)
                        .get(Banda_.LOCALIDAD), filtro.getLocalidad()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    //BUSCA PUBLICACIONES POR ATRIBUTOS DE MÚSICO (PROVINCIA, LOCALIDAD Y/O INSTRUMENTO)
    public Specification<Publicacion> filtrarPublicacionPorMusico(FiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getProvincia() != null) { //busca publicaciones por provincia de usuarios músicos
                predicates.add(criteriaBuilder.equal(root.join(Publicacion_.musico)
                        .get(Banda_.PROVINCIA), filtro.getProvincia()));
            }
            if (filtro.getLocalidad() != null) { //busca publicaciones por localidad de usuarios músicos
                predicates.add(criteriaBuilder.equal(root.join(Publicacion_.musico)
                        .get(Musico_.LOCALIDAD), filtro.getLocalidad()));
            }
            if (filtro.getInstrumento() != null) {//busca publicaciones por instrumento de usuarios músicos
                predicates.add(criteriaBuilder.equal(root.join(Publicacion_.musico)
                        .get(Musico_.INSTRUMENTO), filtro.getInstrumento()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
