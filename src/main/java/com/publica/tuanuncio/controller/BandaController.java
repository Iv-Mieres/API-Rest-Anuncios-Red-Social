package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.get.GetBandaDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionMusicoDTO;
import com.publica.tuanuncio.dto.post.PostBandaDTO;
import com.publica.tuanuncio.dto.FiltroDTO;;
import com.publica.tuanuncio.model.Banda;
import com.publica.tuanuncio.model.Publicacion;
import com.publica.tuanuncio.service.IBandaService;
import com.publica.tuanuncio.service.IPublicacionBandaService;
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
@RequestMapping("bandas")
public class BandaController {

    @Autowired
    private IBandaService bandaService;

    @Autowired
    private IPublicacionBandaService publicacionService;


    //CREAR PERFIL BANDA
    @PreAuthorize("hasRole ('USER')")
    @PostMapping("/crear")
    public ResponseEntity<String> crearPerfilBanda(@Valid @RequestBody Banda banda, HttpSession session){
        bandaService.crearBanda(session, banda);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Su perfil 'Banda' se creó correctamente. Vuelva a loguearse para poder ver su perfil.");
    }

    //MOSTRAR PERFIL
    @PreAuthorize("hasRole ('BANDA')")
    @GetMapping("/mi_perfil")
    public ResponseEntity<GetBandaDTO> verPerfil(HttpSession session){
        return ResponseEntity.ok(bandaService.verPerfilBanda(session));
    }

    //EDITAR USUARIO
    @PreAuthorize("hasRole ('BANDA')")
    @PutMapping("/editar_usuario")
    public ResponseEntity<String> editarUsuario(@Valid @RequestBody PostBandaDTO bandaDTO, HttpSession session){
        bandaService.editarUsuario(session, bandaDTO);
        return ResponseEntity.ok("Su usuario se editó correctamente!");
    }

    //ELIMINAR USUARIO
    @PreAuthorize("hasRole ('BANDA')")
    @DeleteMapping("/eliminar_usuario")
    public ResponseEntity<String> eliminarUsuario(HttpSession session){
        bandaService.eliminarMiPerfil(session);
        return ResponseEntity.ok("Su usuario ha sido eliminado!");
    }

    //----------------- ENDPOINTS PARA PUBLICACIONES ------------------------

    //CREAR PUBLICACIÓN
    @PreAuthorize("hasRole ('BANDA')")
    @PostMapping("/crear_publicacion")
    public ResponseEntity<String> crearPublicacion(@Valid @RequestBody Publicacion publicacion, HttpSession session){
        publicacionService.crearPublicacion(publicacion, session);
        return ResponseEntity.status(HttpStatus.CREATED).body("Su publicación fue creada con exito!");
    }

    //MOSTRAR PUBLICACIONES CREADAS POR 'ROLE_MUSICO', PAGINADAS Y/o FILTRADAS
    @PreAuthorize("hasRole ('BANDA')")
    @GetMapping("/ver_publicaciones_musicos")
    public ResponseEntity<Page<GetPublicacionMusicoDTO>> verPublicacionesDeMusicos(@Valid @RequestBody FiltroDTO filtro,
                                                                                   Pageable pageable){
        return ResponseEntity.ok(publicacionService.verPublicaciones(filtro, pageable));
    }

    //EDITAR PUBLICACIÓN
    @PreAuthorize("hasRole ('BANDA')")
    @PutMapping("/editar_mi_publicacion")
    public ResponseEntity<String> editarMiPublicacion(@Valid @RequestBody Publicacion publicacion, HttpSession session){
        publicacionService.editarPublicacion(publicacion, session);
        return ResponseEntity.ok("Su publicación se editó correctamente.");
    }

    //ELIMINAR PUBLICACIÓN
    @PreAuthorize("hasRole ('BANDA')")
    @DeleteMapping("eliminar_mi_publicacion/{idPublicacion}")
    public ResponseEntity<String> eliminarMiPublicacion(@PathVariable Long idPublicacion, HttpSession session){
        publicacionService.eliminarPublicacion(idPublicacion, session);
       return ResponseEntity.ok("Su publicación se eliminó correctamente.");
    }
}
