package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionBandaDTO;
import com.publica.tuanuncio.model.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface IPublicacionMusicoService {

    void crearPublicacion(Publicacion publicacion, HttpSession session);
    void editarPublicacion(Publicacion publicacion, HttpSession session);
    void eliminarPublicacion(Long idPublicacion, HttpSession session);
    Page<GetPublicacionBandaDTO> verPublicaciones(FiltroDTO filtro, Pageable pageable);
}
