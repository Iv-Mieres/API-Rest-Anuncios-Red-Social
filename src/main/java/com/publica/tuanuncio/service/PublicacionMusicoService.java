package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.PublicacionDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionBandaDTO;
import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.model.Publicacion;
import com.publica.tuanuncio.model.Usuario;
import com.publica.tuanuncio.repository.IPublicacionRepository;
import com.publica.tuanuncio.util.SpecificationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class PublicacionMusicoService implements IPublicacionMusicoService {

    @Autowired
    private IPublicacionRepository publicacionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SpecificationFilter specification;


    //CREA UNA PUBLICACIÓN COMO MÚSICO
    @Override
    public void crearPublicacion(PublicacionDTO publicacionDTO, HttpSession session) {
        var usuario = this.userSession(session);
        this.validarFecha(publicacionDTO.getFechaPublicacion());
        var publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        publicacion.setMusico(usuario.getPerfilMusico());
        publicacionRepository.save(publicacion);
    }

    //MUESTRA PUBLICACIONES DE BANDAS PAGINADAS Y/O FILTRADAS
    @Override
    public Page<GetPublicacionBandaDTO> verPublicaciones(FiltroDTO filtro, Pageable pageable) {
        var publicaciones = publicacionRepository.findAll(specification.filtrarPublicaciones(filtro)
                        .and(specification.filtrarPublicacionPorBanda(filtro)), pageable).stream()
                .filter(p -> p.getBanda() != null)
                .map(publi -> modelMapper.map(publi, GetPublicacionBandaDTO.class))
                .distinct()
                .collect(Collectors.toList());
        return new PageImpl<GetPublicacionBandaDTO>(publicaciones, pageable, publicaciones.size());
    }

    //EDITA UNA PUBLICACIÓN
    @Override
    public void editarPublicacion(PublicacionDTO publicacionDTO, HttpSession session) {
        var usuarioSession = this.userSession(session);
        this.validarFecha(publicacionDTO.getFechaPublicacion());
        if (this.usuarioBDIgualUsuarioSession(publicacionDTO.getIdPublicacion(), usuarioSession)) {
            var publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
            publicacion.setMusico(usuarioSession.getPerfilMusico());
            publicacionRepository.save(publicacion);
        }
        else throw new BadRequestException("El id ingresado no es correcto. Ingrese un id válido");
    }

    //ELIMINA PUBLICACIÓN POR ID
    @Override
    public void eliminarPublicacion(Long idPublicacion, HttpSession session) {
        var usuario = this.userSession(session);
        if (this.usuarioBDIgualUsuarioSession(idPublicacion, usuario)) {
            publicacionRepository.deleteById(idPublicacion);
        }
        else throw new BadRequestException("El id ingresado no es correcto. Ingrese un id válido");
    }


    //	_____________________ VALIDACIONES ___________________________________


    // RETORNA LOS DATOS DEL USUARIO LOGUEADO
    public Usuario userSession(HttpSession session) {
        return (Usuario) session.getAttribute("usersession");
    }

    //VALIDA QUE LA FECHA INGRESADA COINCIDA CON LA FECHA ACTUAL
    public void validarFecha(LocalDate fecha) {
        if (!fecha.isEqual(LocalDate.now())) {
            throw new BadRequestException("La fecha ingresada no es válida. Debe ingresar la fecha actual: " + LocalDate.now());
        }
    }

    //VALIDA QUE EL USUARIO LOGUEADO COINCIDA CON EL USUARIO CONTENIDO EN LA PUBLICACION SELECCIONADA
    public boolean usuarioBDIgualUsuarioSession(Long idPublicacion, Usuario usuario) {
        var publicacionBD = publicacionRepository.findById(idPublicacion);
        if (publicacionBD.map(Publicacion::getMusico).isPresent()) {
            return publicacionBD.stream().anyMatch(p -> p.getMusico().getIdPerfil().equals(usuario.getPerfilMusico().getIdPerfil()));
        }
        else throw new BadRequestException("El id ingresado no es correcto. Ingrese un id válido");
    }
}
