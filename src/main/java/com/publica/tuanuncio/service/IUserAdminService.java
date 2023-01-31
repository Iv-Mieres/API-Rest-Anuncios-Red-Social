package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetUsuarioDTO;
import com.publica.tuanuncio.dto.post.CrearUsuarioDTO;
import com.publica.tuanuncio.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface IUserAdminService {

    void crearAdmin(CrearUsuarioDTO usuario);
    void crearUsuario(CrearUsuarioDTO usuario);
    GetUsuarioDTO verUsuario(Long idUsuario);
    Page<GetUsuarioDTO> listaUsuarios(FiltroDTO filtro, Pageable pageable);
    void editarAdmin(HttpSession session, CrearUsuarioDTO usuarioDTO);
    void eliminarUsuarioComoAdmin(Long idUsuario);
    void elimiarPublicacion(Long idPublicacion);
}
