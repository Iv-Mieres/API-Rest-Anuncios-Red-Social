package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.post.CrearMusicoDTO;
import com.publica.tuanuncio.dto.post.EditarMusicoDTO;
import com.publica.tuanuncio.dto.get.GetMusicoDTO;

import javax.servlet.http.HttpSession;

public interface IMusicoService {

    void crearMusico(HttpSession session, CrearMusicoDTO musico);
    GetMusicoDTO verMiPerfil(HttpSession session);
    void editarUsuario(HttpSession session, EditarMusicoDTO musicoDTO);
    void eliminarMiPerfil(HttpSession session);
}
