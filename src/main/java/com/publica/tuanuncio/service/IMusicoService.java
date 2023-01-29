package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.post.PostMusicoDTO;
import com.publica.tuanuncio.dto.get.GetMusicoDTO;
import com.publica.tuanuncio.model.Musico;

import javax.servlet.http.HttpSession;

public interface IMusicoService {

    void crearMusico(HttpSession session, Musico musico);
    GetMusicoDTO verMiPerfil(HttpSession session);
    void editarUsuario(HttpSession session, PostMusicoDTO musicoDTO);
    void eliminarMiPerfil(HttpSession session);
}
