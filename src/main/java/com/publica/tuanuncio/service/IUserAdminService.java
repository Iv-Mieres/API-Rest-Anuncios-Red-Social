package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetUsuarioDTO;
import com.publica.tuanuncio.dto.post.PostUsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface IUserAdminService {

    void crearAdmin(PostUsuarioDTO usuario);
    void crearUsuario(PostUsuarioDTO usuario);
    Page<GetUsuarioDTO> listaUsuarios(FiltroDTO filtro, Pageable pageable);
    void editarAdmin(HttpSession session, PostUsuarioDTO usuarioDTO);
    void eliminarUsuarioComoAdmin(Long idUsuario);
    void elimiarPublicacion(Long idPublicacion);
}
