package com.publica.tuanuncio.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.publica.tuanuncio.dto.get.GetBandaDTO;
import com.publica.tuanuncio.dto.post.PostBandaDTO;
import com.publica.tuanuncio.dto.post.PostUsuarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.model.Banda;
import com.publica.tuanuncio.model.Role;
import com.publica.tuanuncio.model.Usuario;
import com.publica.tuanuncio.repository.IBandaRepository;
import com.publica.tuanuncio.repository.IRoleRepository;
import com.publica.tuanuncio.repository.IUserAdminRepository;

@Service
public class BandaService implements IBandaService {

    @Autowired
    private IBandaRepository bandaRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserAdminRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;


    // CREA UN PERFIL CON ROLE_USER + ROLE_BANDA
    @Override
    public void crearBanda(HttpSession session, Banda banda) {
        banda.setUnUsuario(this.actualizarUsuario(session));
        bandaRepository.save(banda);
    }

    // MUESTRA LOS DATOS DEL USUARIO 'BANDA'
    @Override
    public GetBandaDTO verPerfilBanda(HttpSession session) {
        var usuarioBD = usuarioRepository.findById(this.idSession(session));

        // convierte el 'PerfilBanda' del usuarioBD en tipo BandaDTO y retorna los datos
        return usuarioBD.stream()
                .map(usuario -> modelMapper.map(usuario.getPerfilBanda(), GetBandaDTO.class))
                .findFirst().get();
    }

    //EDITA TODOS LOS DATOS DEL USUARIO BANDA
    @Override
    public void editarUsuario(HttpSession session, PostBandaDTO bandaDTO) {

        var usuario = this.userSession(session);
        this.validarDatosAlEditar(bandaDTO.getUsuario(), usuario.getUsername(), usuario.getEmail());

        //Edita y guarda los datos del nuevo usuario
        var usuarioEditado = modelMapper.map(bandaDTO.getUsuario(), Usuario.class);
        usuarioEditado.setIdUsuario(usuario.getIdUsuario());
        usuarioEditado.setEliminado("false");
        usuarioEditado.setPassword(passwordEncoder.encode(bandaDTO.getUsuario().getPassword()));
        usuarioEditado.setRoles(usuario.getRoles());
        usuarioRepository.save(usuarioEditado);

        //edita y guarda los datos del nuevo perfil
        var bandaEditada = modelMapper.map(bandaDTO, Banda.class);
        bandaEditada.setIdPerfil(usuario.getPerfilBanda().getIdPerfil());
        bandaEditada.setPublicacionesBandas(usuario.getPerfilBanda().getPublicacionesBandas());
        bandaEditada.setUnUsuario(usuario);
        bandaRepository.save(bandaEditada);

    }

    //ELIMINA EL USUARIO DE FORMA LÓGICA Y BORRA EL PERFIL, LAS PUBLICACIONES Y EL 'ROLE_BANDA' DE LA BD
    @Override
    public void eliminarMiPerfil(HttpSession session) {
        var usuario = this.userSession(session);
        usuario.setEliminado("true");
        usuario.setRoles(Set.of(roleRepository.findByRoleAuthority("USER")));
        usuarioRepository.save(usuario);
        bandaRepository.deleteById(usuario.getPerfilBanda().getIdPerfil());
    }


//	_____________________  VALIDACIONES   _________________________________


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
      -SI EL USUARIO LOGUEADO YA CONTIENE UN ROLE 'BANDA' LANZA UNA EXCEPTION */
    public Usuario actualizarUsuario(HttpSession session) {
        var usuario = (Usuario) session.getAttribute("usersession");

        if (!Objects.isNull(usuario.getPerfilBanda())) {
            throw new BadRequestException("Su usuario ya contiene un perfil creado.");
        }
        var nuevoRole = roleRepository.findByRoleAuthority("BANDA");
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
       -QUE LOS DATOS INGRESADOS SEAN CORRECTOS */
    public void validarDatosAlEditar(PostUsuarioDTO usuario, String username, String email) {
        if ((usuarioRepository.existsByUsername(usuario.getUsername()) && !usuario.getUsername().equals(username))
                || (usuarioRepository.existsByEmail(usuario.getEmail()) && !usuario.getEmail().equals(email))) {
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
