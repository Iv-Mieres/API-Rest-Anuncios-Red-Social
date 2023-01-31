package com.publica.tuanuncio.service;

import javax.servlet.http.HttpSession;

import com.publica.tuanuncio.dto.get.GetBandaDTO;
import com.publica.tuanuncio.dto.post.CrearBandaDTO;
import com.publica.tuanuncio.dto.post.EditarBandaDTO;
import com.publica.tuanuncio.model.Banda;


public interface IBandaService {

	public void crearBanda(HttpSession session, CrearBandaDTO bandaDTO);
	public GetBandaDTO verPerfilBanda(HttpSession session);
    void editarUsuario(HttpSession session, EditarBandaDTO banda);
    void eliminarMiPerfil(HttpSession session);
}
