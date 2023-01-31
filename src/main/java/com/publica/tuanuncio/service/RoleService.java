package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.get.GetRoleDTO;
import com.publica.tuanuncio.dto.post.CrearYEditarRoleDTO;
import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.model.Role;
import com.publica.tuanuncio.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    //CREAR ROLE
    @Override
    public void crearRole(CrearYEditarRoleDTO roleDTO){
        if (roleRepository.existsByRoleAuthority(roleDTO.getRoleAuthority())){
            throw new BadRequestException("El role ingresado ya existe. Ingrese un nuevo role");
        }
        var role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
    }

    //VER ROLES
    @Override
    public List<GetRoleDTO> roles() {
        return   roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, GetRoleDTO.class))
                .collect(Collectors.toList());
    }

    //EDITAR ROLE
    @Override
    public void editarRole(CrearYEditarRoleDTO roleDTO){
        var roleBD = roleRepository.findById(roleDTO.getIdRole())
                .orElseThrow(() -> new BadRequestException("El id " + roleDTO.getIdRole() + " no existe. Ingrese un id válido"));
        //comprueba que el role no exista en la base de datos
        if (roleRepository.existsByRoleAuthority(roleDTO.getRoleAuthority())
                && !roleBD.getRoleAuthority().equals(roleDTO.getRoleAuthority())){
            throw new BadRequestException("El role ingresado ya existe. Ingrese un nuevo role");
        }
        var role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
    }

    //ELIMINAR ROLE
    @Override
    public void eliminarRole(Long idRole){
        roleRepository.deleteById(idRole);
    }

}
