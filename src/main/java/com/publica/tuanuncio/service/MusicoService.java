package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.post.CrearMusicoDTO;
import com.publica.tuanuncio.dto.post.EditarMusicoDTO;
import com.publica.tuanuncio.dto.post.CrearUsuarioDTO;
import com.publica.tuanuncio.dto.get.GetMusicoDTO;
import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.model.Musico;
import com.publica.tuanuncio.model.Role;
import com.publica.tuanuncio.model.Usuario;
import com.publica.tuanuncio.repository.IMusicoRepository;
import com.publica.tuanuncio.repository.IRoleRepository;
import com.publica.tuanuncio.repository.IUserAdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MusicoService implements IMusicoService {

    @Autowired
    private IMusicoRepository musicoRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserAdminRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;


    // CREA UN PERFIL CON ROLE_USER + ROLE_MUSICO
    @Override
    public void crearMusico(HttpSession session, CrearMusicoDTO musicoDTO) {
        var musico = modelMapper.map(musicoDTO, Musico.class);
        musico.setUnUsuario(this.actualizarUsuario(session));
        musicoRepository.save(musico);
    }

    // MUESTRA LOS DATOS DEL USUARIO 'MUSICO'
    @Override
    public GetMusicoDTO verMiPerfil(HttpSession session) {
        var usuarioBD = usuarioRepository.findById(this.idSession(session));

        // convierte el 'PerfilMusico' del usuarioBD en tipo MusicoDTO y retorna los datos
        return usuarioBD.stream()
                .map(usuario -> modelMapper.map(usuario.getPerfilMusico(), GetMusicoDTO.class))
                .findFirst().get();
    }

    //EDITA TODOS LOS DATOS DEL USUARIO MUSICO
    @Override
    public void editarUsuario(HttpSession session, EditarMusicoDTO musicoDTO) {
        var usuario = this.userSession(session);

        // Se comprueba que los datos ingresados sean correctos
        this.validarDatosAlEditar(musicoDTO.getUsuario(), usuario.getUsername(), usuario.getEmail());

        var usuarioEditado = modelMapper.map(musicoDTO.getUsuario(), Usuario.class);
        usuarioEditado.setIdUsuario(usuario.getIdUsuario());
        usuarioEditado.setEliminado("false");
        usuarioEditado.setPassword(passwordEncoder.encode(musicoDTO.getUsuario().getPassword()));
        usuarioEditado.setRoles(usuario.getRoles());
        usuarioRepository.save(usuarioEditado);

        var musicoEditado = modelMapper.map(musicoDTO, Musico.class);
        musicoEditado.setIdPerfil(usuario.getPerfilMusico().getIdPerfil());
        musicoEditado.setPublicacionesMusicos(usuario.getPerfilMusico().getPublicacionesMusicos());
        musicoEditado.setUnUsuario(usuario);
        musicoRepository.save(musicoEditado);
    }

    //ELIMINA EL USUARIO DE FORMA LÓGICA Y BORRA EL PERFIL 'MUSICO' DE LA BASE DE DATOS
    @Override
    public void eliminarMiPerfil(HttpSession session) {
        var usuario = this.userSession(session);
        usuario.setEliminado("true");
        usuario.setRoles(Set.of(roleRepository.findByRoleAuthority("USER")));
        usuarioRepository.save(usuario);
        musicoRepository.deleteById(usuario.getPerfilMusico().getIdPerfil());
    }


    //	__________________  VALIDACIONES _____________________________


    // RETORNA EL ID DEL USUARIO LOGUEADO
    public Long idSession(HttpSession session) {
        var userSession = (Usuario) session.getAttribute("usersession");
        return userSession.getIdUsuario();
    }

    // RETORNA LOS DATOS DEL USUARIO LOGUEADO
    public Usuario userSession(HttpSession session) {
        return (Usuario) session.getAttribute("usersession");
    }

    /*
      -CREA EL NUEVO ROLE 'BANDA', LO ASIGNA AL USUARIO LOGUEADO Y RETORNA LOS DATOS DE LA SESSION
      -SI EL USUARIO LOGUEADO YA CONTIENE UN ROLE 'BANDA' LANZA UNA EXCEPTION
    */
    public Usuario actualizarUsuario(HttpSession session) {
        var usuario = (Usuario) session.getAttribute("usersession");

        if (!Objects.isNull(usuario.getPerfilMusico())) {
            throw new BadRequestException("Su usuario ya contiene un perfil creado.");
        }
        var nuevoRole = roleRepository.findByRoleAuthority("MUSICO");
        Set<Role> roles = new HashSet<>();
        roles.addAll(usuario.getRoles());
        roles.add(nuevoRole);
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return usuario;
    }

    /*
   VALIDA:
       -QUE EL EMAIL Y USERNAME INGRESADOS NO EXISTAN EN LA BASE DE DATOS
       -QUE EL EMAIL Y USERNAME SEAN IGUALES A LOS DATOS DEL USUARIO QUE SE QUIERE EDITAR
       -QUE LOS DATOS INGRESADOS SEAN CORRECTOS
    */
    public void validarDatosAlEditar(CrearUsuarioDTO usuario, String usernameSession, String emailSession) {
        if ((usuarioRepository.existsByUsername(usuario.getUsername())
                && !usuario.getUsername().equals(usernameSession)) ||
                (usuarioRepository.existsByEmail(usuario.getEmail()) && !usuario.getEmail().equals(emailSession))) {
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
