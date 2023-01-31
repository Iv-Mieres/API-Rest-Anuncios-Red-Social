package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetUsuarioDTO;
import com.publica.tuanuncio.dto.post.CrearUsuarioDTO;
import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.model.Usuario;
import com.publica.tuanuncio.repository.*;
import com.publica.tuanuncio.util.SpecificationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAdminService implements IUserAdminService {

    @Autowired
    private IUserAdminRepository userAdminRepository;
    @Autowired
    private IBandaRepository bandaRepository;
    @Autowired
    private IMusicoRepository musicoRepository;
    @Autowired
    private IPublicacionRepository publicacionRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private SpecificationFilter specification;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;


    // CREA UN USUARIO ADMIN
    @Override
    public void crearAdmin(CrearUsuarioDTO usuario) {
        this.validarDatos(usuario);
        var user = modelMapper.map(usuario, Usuario.class);

        if (!user.getEmail().equals("admin@encuentro-musical.com")) {
            throw new BadRequestException("El email ingresado no es válido. Ingrese un email de administrador");
        }
        user.setRoles(Set.of(roleRepository.findByRoleAuthority("ADMIN")));
        user.setPassword(passwordEncoder.encode(usuario.getPassword()));
        user.setEliminado("false");
        userAdminRepository.save(user);
    }

    //CREA UN USUARIO CON 'ROLE_USER'
    @Override
    public void crearUsuario(CrearUsuarioDTO usuario) {
        this.validarDatos(usuario);
        var user = modelMapper.map(usuario, Usuario.class);
        user.setRoles(Set.of(roleRepository.findByRoleAuthority("USER")));
        user.setPassword(passwordEncoder.encode(usuario.getPassword()));
        user.setEliminado("false");
        userAdminRepository.save(user);
    }

    //MUESTRA UN USUARIO POR ID
    @Override
    public GetUsuarioDTO verUsuario(Long idUsuario){
      var usuario = userAdminRepository.findById(idUsuario)
              .orElseThrow(() -> new BadRequestException("El usuario con id: " + idUsuario + " no se encuentra registrado."));
      return modelMapper.map(usuario, GetUsuarioDTO.class);

    }

    //MUESTRA UNA LISTA DE USUARIOS PAGINADOS QUE PUEDE FILTRARSE POR ID, EMAIL Y/O USERNAME
    @Override
    public Page<GetUsuarioDTO> listaUsuarios(FiltroDTO filtro, Pageable pageable) {
        var usuarios = userAdminRepository.findAll(specification.filrarUsuario(filtro), pageable)
                .stream()
                .map(usuario -> modelMapper.map(usuario, GetUsuarioDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<GetUsuarioDTO> (usuarios, pageable, usuarios.size());
    }

    //EDITAR DATOS DE ADMINISTRADOR
    @Override
    public void editarAdmin(HttpSession session, CrearUsuarioDTO usuarioDTO) {
        var usuario = (Usuario) session.getAttribute("usersession");
        this.validarAlEditar(usuarioDTO, usuario.getUsername(), usuario.getEmail());

        if (!usuario.getEmail().equals("admin@encuentro-musical.com")) {
            throw new BadRequestException("El email ingresado no es válido. Ingrese un email de administrador");
        }
        var adminEditado = modelMapper.map(usuarioDTO, Usuario.class);
        adminEditado.setEliminado("false");
        adminEditado.setRoles(Set.of(roleRepository.findByRoleAuthority("ADMIN")));
        adminEditado.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        adminEditado.setIdUsuario(usuario.getIdUsuario());
        userAdminRepository.save(adminEditado);
    }

    // ELIMINADO LÓGICO DE USUARIO POR ID Y BORRADO DE PERFILES
    @Override
    public void eliminarUsuarioComoAdmin(Long idUsuario) {
        var usuarioBD =userAdminRepository.findById(idUsuario)
                .orElseThrow(() -> new BadRequestException("El id " + idUsuario + " no existe en la base de datos."));

        usuarioBD.setEliminado("true");
        userAdminRepository.save(usuarioBD);

        if (!Objects.isNull(usuarioBD.getPerfilBanda())) {
            bandaRepository.delete(usuarioBD.getPerfilBanda());
        }
        else if (!Objects.isNull((usuarioBD.getPerfilMusico()))){
            musicoRepository.delete(usuarioBD.getPerfilMusico());
        }
    }

    // ELIMINAR PUBLICACIÓN POR ID
    @Override
    public void elimiarPublicacion(Long idPublicacion){
       if (!publicacionRepository.existsById(idPublicacion)){
           throw new BadRequestException("El id " + idPublicacion + " no existe. Ingrese un nuevo id");
       }
        publicacionRepository.deleteById(idPublicacion);
    }


    //----------------- VALIDACIONES --------------------------

    /* VALIDA QUE EL EMAIL Y USERNAME INGRESADOS NO EXISTAN EN LA BASE DE DATOS
       Y QUE LOS DATOS INGRESADOS SEAN CORRECTOS AL GUARDAR*/
    public void validarDatos(CrearUsuarioDTO usuario) {
        if (userAdminRepository.existsByUsername(usuario.getUsername())) {
            throw new BadRequestException("El username " + usuario.getUsername() + " ya se encuentra registrado.");
        }
        if (userAdminRepository.existsByEmail(usuario.getEmail())){
            throw new BadRequestException("El email " + usuario.getEmail() + " ya se encuentra registrado.");
        }
        if (!usuario.getEmail().equals(usuario.getRepeatEmail())) {
            throw new BadRequestException("Los datos ingresados en los campos Email y Repeat Email deben coincidir");
        }
        if (!usuario.getPassword().equals(usuario.getRepeatPassword())) {
            throw new BadRequestException("Los datos ingresados en los campos Password y Repeat Password deben coincidir");
        }
    }

    //VALIDA QUE LOS DATOS INGRESADOS SEAN CORRECTOS AL EDITAR
    public void validarAlEditar(CrearUsuarioDTO usuario, String username, String email){
        if ((userAdminRepository.existsByUsername(usuario.getUsername()) && !usuario.getUsername().equals(username))
                || (userAdminRepository.existsByEmail(usuario.getEmail()) && !usuario.getEmail().equals(email))) {
            throw new BadRequestException("El username o email ingresados ya se encuentran registrados.");
        }
        if (!usuario.getEmail().equals(usuario.getRepeatEmail())) {
            throw new BadRequestException("Los datos ingresados en los campos Email y Repeat Email deben coincidir");
        }
        if (!usuario.getPassword().equals(usuario.getRepeatPassword())) {
            throw new BadRequestException("Los datos ingresados en los campos Password y Repeat Password deben coincidir");
        }
    }

}
