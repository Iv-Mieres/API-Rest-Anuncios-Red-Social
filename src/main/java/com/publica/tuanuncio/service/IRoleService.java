package com.publica.tuanuncio.service;

import com.publica.tuanuncio.model.Role;

import java.util.List;

public interface IRoleService {

    void crearRole(Role role);
    List<Role> roles();
    void editarRole(Role role);
    void eliminarRole(Long idRole);
}
