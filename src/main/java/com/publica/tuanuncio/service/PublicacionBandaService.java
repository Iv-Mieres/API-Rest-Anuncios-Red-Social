package com.publica.tuanuncio.service;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionMusicoDTO;
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
public class PublicacionBandaService implements IPublicacionBandaService {

    @Autowired
    private IPublicacionRepository publicacionRepository;
    @Autowired
    private SpecificationFilter specification;
    @Autowired
    private ModelMapper modelMapper;


    //CREAR UNA PUBLICACIÓN COMO BANDA
    @Override
    public void crearPublicacion(Publicacion publicacion, HttpSession session) {
        var usuario = this.userSession(session);
        this.validarFecha(publicacion.getFechaPublicacion());
        publicacion.setBanda(usuario.getPerfilBanda());
        publicacionRepository.save(publicacion);
    }

    //MUESTRA PUBLICACIONES PAGINADAS Y/O FILTRADAS
    @Override
    public Page<GetPublicacionMusicoDTO> verPublicaciones(FiltroDTO filtro, Pageable pageable) {
        var publicaciones = publicacionRepository.findAll(specification.filtrarPublicaciones(filtro)
                        .and(specification.filtrarPublicacionPorMusico(filtro)), pageable)
                .stream()
                .filter(publi -> publi.getMusico() != null)
                .map(publi -> modelMapper.map(publi, GetPublicacionMusicoDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<GetPublicacionMusicoDTO>(publicaciones, pageable, publicaciones.size());
    }

    //EDITA UNA PUBLICACIÓN
    @Override
    public void editarPublicacion(Publicacion publicacion, HttpSession session) {
        var usuario = this.userSession(session);
        this.validarFecha(publicacion.getFechaPublicacion());
        if(this.usuarioBDIgualUsuarioSession(publicacion.getIdPublicacion(), usuario)){
            publicacion.setBanda(usuario.getPerfilBanda());
            publicacionRepository.save(publicacion);
        }
        else throw new BadRequestException("El id ingresado no es correcto. Ingrese un id válido");
    }

    //ELIMINA UNA PUBLICACION POR ID
    @Override
    public void eliminarPublicacion(Long idPublicacion, HttpSession session) {
        var usuario = this.userSession(session);
        if(this.usuarioBDIgualUsuarioSession(idPublicacion, usuario)){
            publicacionRepository.deleteById(idPublicacion);
        }
        else throw new BadRequestException("El id ingresado no es correcto. Ingrese un id válido");
    }

    //	____________________ VALIDACIONES _____________________________


    // RETORNA LOS DATOS DEL USUARIO LOGUEADO
    public Usuario userSession(HttpSession session) {
        return (Usuario) session.getAttribute("usersession");
    }

    //VÁLIDA QUE LA FECHA INGRESADA COINCIDA CON LA FECHA ACTUAL
    public void validarFecha(LocalDate fecha) {
        if (!fecha.isEqual(LocalDate.now())) {
            throw new BadRequestException("La fecha ingresada no es válida. Debe ingresar la fecha actual: " + LocalDate.now());
        }
    }

    //VALIDA QUE EL USUARIO LOGUEADO COINCIDA CON EL USUARIO CONTENIDO EN LA PUBLICACION SELECCIONADA
    public boolean usuarioBDIgualUsuarioSession(Long idPublicacion, Usuario usuario){
        var publicacionBD = publicacionRepository.findById(idPublicacion);
        if (publicacionBD.map(Publicacion::getBanda).isPresent()){
           return publicacionBD.stream().anyMatch(p -> p.getBanda().getIdPerfil().equals(usuario.getPerfilBanda().getIdPerfil()));
        }
        else throw new BadRequestException("El id ingresado no es correcto. Ingrese un id válido");
    }
}
