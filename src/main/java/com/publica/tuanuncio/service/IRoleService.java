package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.get.GetRoleDTO;
import com.publica.tuanuncio.dto.post.CrearYEditarRoleDTO;

import java.util.List;

public interface IRoleService {

    void crearRole(CrearYEditarRoleDTO role);
    List<GetRoleDTO> roles();
    void editarRole(CrearYEditarRoleDTO role);
    void eliminarRole(Long idRole);
}
