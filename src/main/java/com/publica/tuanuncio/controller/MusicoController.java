package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.post.PostMusicoDTO;
import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetMusicoDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionBandaDTO;
import com.publica.tuanuncio.model.Musico;
import com.publica.tuanuncio.model.Publicacion;
import com.publica.tuanuncio.service.IMusicoService;
import com.publica.tuanuncio.service.IPublicacionMusicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("musicos")
public class MusicoController {

    @Autowired
    private IMusicoService musicoService;
    @Autowired
    private IPublicacionMusicoService publicacionService;


    //CREAR PERFIL MUSICO
    @PreAuthorize("hasRole ('USER')")
    @PostMapping("/crear_mi_perfil")
    public ResponseEntity<String> crearPerfilMusico(@Valid @RequestBody Musico musico, HttpSession session){
        musicoService.crearMusico(session, musico);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Su perfil 'Musico' se creó correctamente. Por favor, vuelva a loguearse!");
    }

    //MOSTRAR PERFIL
    @PreAuthorize("hasRole ('MUSICO')")
    @GetMapping("/ver_mi_perfil")
    public ResponseEntity<GetMusicoDTO> verPerfil(HttpSession session){
        return ResponseEntity.ok(musicoService.verMiPerfil(session));
    }

    //EDITAR USUARIO
    @PreAuthorize("hasRole ('MUSICO')")
    @PutMapping("/editar_usuario")
    public ResponseEntity<String> editarUsuario(@Valid @RequestBody PostMusicoDTO musicoDTO, HttpSession session){
        musicoService.editarUsuario(session, musicoDTO);
        return ResponseEntity.ok("Su usuario se editó correctamente!");
    }

    //ELIMINAR USUARIO
    @PreAuthorize("hasRole ('MUSICO')")
    @DeleteMapping("/eliminar_usuario")
    public ResponseEntity<String> eliminarUsuario(HttpSession session){
        musicoService.eliminarMiPerfil(session);
        return ResponseEntity.ok("Su usuario ha sido eliminado!");
    }

    //-------------- ENDPOINTS PARA PUBLICACIONES -------------------------

    //CREAR PUBLICACIÓN
    @PreAuthorize("hasRole ('MUSICO')")
    @PostMapping("/crear_publicacion")
    public ResponseEntity<String> crearPublicacion(@Valid @RequestBody Publicacion publicacion, HttpSession session){
        publicacionService.crearPublicacion(publicacion, session);
        return ResponseEntity.ok("Su publicación fue creada con exito!");
    }

    //MOSTRAR PUBLICACIONES CREADAS POR 'ROLE_MUSICO', PAGINADAS Y/o FILTRADAS
    @PreAuthorize("hasRole ('MUSICO')")
    @GetMapping("/ver_publicaciones_bandas")
    public ResponseEntity<Page<GetPublicacionBandaDTO>> verPublicacionesDeBandas(@RequestBody FiltroDTO filtro, Pageable pageable){
        return ResponseEntity.ok(publicacionService.verPublicaciones(filtro, pageable));
    }

    //EDITAR PUBLICACIÓN
    @PreAuthorize("hasRole ('MUSICO')")
    @PutMapping("/editar_mi_publicacion")
    public ResponseEntity<String> editarMiPublicacion(@Valid @RequestBody Publicacion publicacion, HttpSession session){
        publicacionService.editarPublicacion(publicacion, session);
        return ResponseEntity.ok("Su publicación se editó correctamente.");
    }

    //ELIMINAR PUBLICACIÓN
    @PreAuthorize("hasRole ('MUSICO')")
    @DeleteMapping("eliminar_mi_publicacion")
    public ResponseEntity<String> eliminarMiPublicacion(@PathVariable Long idPublicacion, HttpSession session){
        publicacionService.eliminarPublicacion(idPublicacion, session);
        return ResponseEntity.ok("Su publicación se eliminó correctamente.");
    }

}
