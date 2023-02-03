package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.PublicacionDTO;
import com.publica.tuanuncio.dto.post.CrearMusicoDTO;
import com.publica.tuanuncio.dto.post.EditarMusicoDTO;
import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetMusicoDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionBandaDTO;
import com.publica.tuanuncio.service.IMusicoService;
import com.publica.tuanuncio.service.IPublicacionMusicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Crea un perfil 'Músico'")
    public ResponseEntity<String> crearPerfilMusico(@Valid @RequestBody CrearMusicoDTO musico, HttpSession session){
        musicoService.crearMusico(session, musico);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Su perfil 'Musico' se creó correctamente. Por favor, vuelva a loguearse!");
    }

    //MOSTRAR PERFIL
    @PreAuthorize("hasRole ('MUSICO')")
    @GetMapping("/ver_mi_perfil")
    @Operation(summary = "Muestra los datos del usuario 'Músico' y sus publicaciones creadas")
    public ResponseEntity<GetMusicoDTO> verPerfil(HttpSession session){
        return ResponseEntity.ok(musicoService.verMiPerfil(session));
    }

    //EDITAR USUARIO
    @PreAuthorize("hasRole ('MUSICO')")
    @PutMapping("/editar_usuario")
    @Operation(summary = "Edita el usuario 'Músico'. Desde este endpoint no es posible editar sus publicaciones")
    public ResponseEntity<String> editarUsuario(@Valid @RequestBody EditarMusicoDTO musicoDTO, HttpSession session){
        musicoService.editarUsuario(session, musicoDTO);
        return ResponseEntity.ok("Su usuario se editó correctamente!");
    }

    //ELIMINAR USUARIO
    @PreAuthorize("hasRole ('MUSICO')")
    @DeleteMapping("/eliminar_usuario")
    @Operation(summary = "Elimina el usuario de forma lógica y borra de forma permanente el perfil y las publicaciones creadas.")
    public ResponseEntity<String> eliminarUsuario(HttpSession session){
        musicoService.eliminarMiPerfil(session);
        return ResponseEntity.ok("Su usuario ha sido eliminado!");
    }

    //-------------- ENDPOINTS PARA PUBLICACIONES -------------------------

    //CREAR PUBLICACIÓN
    @PreAuthorize("hasRole ('MUSICO')")
    @PostMapping("/crear_publicacion")
    @Operation(summary = "Crea una publicación.")
    public ResponseEntity<String> crearPublicacion(@Valid @RequestBody PublicacionDTO publicacion, HttpSession session){
        publicacionService.crearPublicacion(publicacion, session);
        return ResponseEntity.ok("Su publicación fue creada con exito!");
    }

    //MOSTRAR PUBLICACIONES CREADAS POR 'ROLE_MUSICO', PAGINADAS Y/o FILTRADAS
    @PreAuthorize("hasAnyRole ('MUSICO', 'BANDA', 'MUSICO')")
    @GetMapping("/ver_publicaciones_bandas")
    @Operation(summary = "Devuelve una lista de publicaciones paginadas y/o filtradas. La utilización de los filtros es opcional.")
    public ResponseEntity<Page<GetPublicacionBandaDTO>> verPublicacionesDeBandas(
            @Parameter(description = "Las busquedas solo se pueden filtrar por fechaPublicacion, generoMusical, " +
                    "provincia y/o localidad. Si se coloca un valor null se ignora el filtrado.")
            @RequestBody FiltroDTO filtro, Pageable pageable){
        return ResponseEntity.ok(publicacionService.verPublicaciones(filtro, pageable));
    }

    //EDITAR PUBLICACIÓN
    @PreAuthorize("hasRole ('MUSICO')")
    @PutMapping("/editar_mi_publicacion")
    @Operation(summary = "Edita una publicacion por id.")
    public ResponseEntity<String> editarMiPublicacion(@Valid @RequestBody PublicacionDTO publicacion, HttpSession session){
        publicacionService.editarPublicacion(publicacion, session);
        return ResponseEntity.ok("Su publicación se editó correctamente.");
    }

    //ELIMINAR PUBLICACIÓN
    @PreAuthorize("hasRole ('MUSICO')")
    @DeleteMapping("eliminar_mi_publicacion")
    @Operation(summary = "Elimina una publicación por id")
    public ResponseEntity<String> eliminarMiPublicacion(@PathVariable Long idPublicacion, HttpSession session){
        publicacionService.eliminarPublicacion(idPublicacion, session);
        return ResponseEntity.ok("Su publicación se eliminó correctamente.");
    }

}
