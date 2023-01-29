package com.publica.tuanuncio.service;

import javax.servlet.http.HttpSession;

import com.publica.tuanuncio.dto.get.GetBandaDTO;
import com.publica.tuanuncio.dto.post.PostBandaDTO;
import com.publica.tuanuncio.model.Banda;


public interface IBandaService {

	public void crearBanda(HttpSession session, Banda banda);
	public GetBandaDTO verPerfilBanda(HttpSession session);
    void editarUsuario(HttpSession session, PostBandaDTO banda);
    void eliminarMiPerfil(HttpSession session);
}
