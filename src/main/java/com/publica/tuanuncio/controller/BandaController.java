package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.PublicacionDTO;
import com.publica.tuanuncio.dto.get.GetBandaDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionMusicoDTO;
import com.publica.tuanuncio.dto.post.CrearBandaDTO;
import com.publica.tuanuncio.dto.post.EditarBandaDTO;
import com.publica.tuanuncio.dto.FiltroDTO;;
import com.publica.tuanuncio.service.IBandaService;
import com.publica.tuanuncio.service.IPublicacionBandaService;
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
@RequestMapping("bandas")
public class BandaController {

    @Autowired
    private IBandaService bandaService;
    @Autowired
    private IPublicacionBandaService publicacionService;


    //CREAR PERFIL BANDA
    @PreAuthorize("hasRole ('USER')")
    @PostMapping("/crear")
    @Operation(summary = "Crea un perfil 'Banda'")
    public ResponseEntity<String> crearPerfilBanda(@Valid @RequestBody CrearBandaDTO bandaDTO, HttpSession session) {
        bandaService.crearBanda(session, bandaDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Su perfil 'Banda' se creó correctamente. Vuelva a loguearse para poder ver su perfil.");
    }

    //MOSTRAR PERFIL
    @PreAuthorize("hasRole ('BANDA')")
    @GetMapping("/mi_perfil")
    @Operation(summary = "Muestra los datos del usuario 'Banda' y sus publicaciones creadas")
    public ResponseEntity<GetBandaDTO> verPerfil(HttpSession session) {
        return ResponseEntity.ok(bandaService.verPerfilBanda(session));
    }

    //EDITAR USUARIO
    @PreAuthorize("hasRole ('BANDA')")
    @PutMapping("/editar_usuario")
    @Operation(summary = "Edita el usuario 'Banda'. Desde este endpoint no es posible editar sus publicaciones")
    public ResponseEntity<String> editarUsuario(@Valid @RequestBody EditarBandaDTO bandaDTO, HttpSession session) {
        bandaService.editarUsuario(session, bandaDTO);
        return ResponseEntity.ok("Su usuario se editó correctamente!");
    }

    //ELIMINAR USUARIO
    @PreAuthorize("hasRole ('BANDA')")
    @DeleteMapping("/eliminar_usuario")
    @Operation(summary = "Elimina el usuario de forma lógica y borra de forma permanente el perfil y las publicaciones creadas.")
    public ResponseEntity<String> eliminarUsuario(HttpSession session) {
        bandaService.eliminarMiPerfil(session);
        return ResponseEntity.ok("Su usuario ha sido eliminado!");
    }

    //----------------- ENDPOINTS PARA PUBLICACIONES ------------------------

    //CREAR PUBLICACIÓN
    @PreAuthorize("hasRole ('BANDA')")
    @PostMapping("/crear_publicacion")
    @Operation(summary = "Crea una publicación.")
    public ResponseEntity<String> crearPublicacion(@Valid @RequestBody PublicacionDTO publicacion, HttpSession session) {
        publicacionService.crearPublicacion(publicacion, session);
        return ResponseEntity.status(HttpStatus.CREATED).body("Su publicación fue creada con exito!");
    }

    //EDITAR PUBLICACIÓN
    @PreAuthorize("hasRole ('BANDA')")
    @PutMapping("/editar_mi_publicacion")
    @Operation(summary = "Edita una publicacion por id.")
    public ResponseEntity<String> editarMiPublicacion(@Valid @RequestBody PublicacionDTO publicacion, HttpSession session) {
        publicacionService.editarPublicacion(publicacion, session);
        return ResponseEntity.ok("Su publicación se editó correctamente.");
    }

    //ELIMINAR PUBLICACIÓN
    @PreAuthorize("hasRole ('BANDA')")
    @DeleteMapping("eliminar_mi_publicacion/{idPublicacion}")
    @Operation(summary = "Elimina una publicación por id")
    public ResponseEntity<String> eliminarMiPublicacion(@PathVariable Long idPublicacion, HttpSession session) {
        publicacionService.eliminarPublicacion(idPublicacion, session);
        return ResponseEntity.ok("Su publicación se eliminó correctamente.");
    }
}
