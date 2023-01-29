package com.publica.tuanuncio.service;

import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.model.Role;
import com.publica.tuanuncio.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    //CREAR ROLE
    @Override
    public void crearRole(Role role){
        if (roleRepository.existsByRoleAuthority(role.getRoleAuthority())){
            throw new BadRequestException("El role ingresado ya existe. Ingrese un nuevo role");
        }
        roleRepository.save(role);
    }

    //VER ROLES
    @Override
    public List<Role> roles() {
        return roleRepository.findAll();
    }

    //EDITAR ROLE
    @Override
    public void editarRole(Role role){
        var roleBD = roleRepository.findById(role.getIdRole())
                .orElseThrow(() -> new BadRequestException("El id " + role.getIdRole() + " no existe. " +
                        "Ingrese un id válido"));
        //comprueba que el role no exista en la base de datos
        if (roleRepository.existsByRoleAuthority(role.getRoleAuthority())
                && !roleBD.getRoleAuthority().equals(role.getRoleAuthority())){
            throw new BadRequestException("El role ingresado ya existe. Ingrese un nuevo role");
        }
        roleRepository.save(role);
    }

    //ELIMINAR ROLE
    @Override
    public void eliminarRole(Long idRole){
        roleRepository.deleteById(idRole);
    }

}
