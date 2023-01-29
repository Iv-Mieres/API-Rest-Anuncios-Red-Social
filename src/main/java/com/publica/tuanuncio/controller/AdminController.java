package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetUsuarioDTO;
import com.publica.tuanuncio.dto.post.PostUsuarioDTO;
import com.publica.tuanuncio.service.IUserAdminService;
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
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private IUserAdminService adminService;

    //CREA USUARIO ADMIN
    @PostMapping("/crear")
    public ResponseEntity<String> crearAdmin(@Valid @RequestBody PostUsuarioDTO admin){
        adminService.crearAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Su usuario 'admin' ha sido creado con exito!");
    }

    //MUESTRA UNA LISTA DE USUARIOS PAGINADOS QUE PUEDE FILTRARSE POR ID, EMAIL Y/O USERNAME
    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping("/ver_lista_usuarios")
    public ResponseEntity<Page<GetUsuarioDTO>> getUsuarios(@RequestBody FiltroDTO filtro, Pageable pageable){
        return ResponseEntity.ok(adminService.listaUsuarios(filtro, pageable));
    }

    //EDITA LOS DATOS DE ADMINISTRADOR
    @PreAuthorize("hasRole ('ADMIN')")
    @PutMapping("/editar_admin")
    public ResponseEntity<String> editarAdmin(@Valid @RequestBody PostUsuarioDTO usuarioDTO, HttpSession session){
        adminService.editarAdmin(session,usuarioDTO);
        return ResponseEntity.ok("Su usuario 'admin' ha sido editado correctamente!");
    }

    //ELIMINA UN USUARIO POR ID
    @PreAuthorize("hasRole ('ADMIN')")
    @DeleteMapping("/eliminar_usuario/{idUsuario}")
    public ResponseEntity<String> eliminarUsuarios(@PathVariable Long idUsuario){
        adminService.eliminarUsuarioComoAdmin(idUsuario);
        return ResponseEntity.ok("El usuario con id " + idUsuario +" se eliminó correctamente!");
    }

    //ELIMINA UNA PUBLICACIÓN POR ID
    @PreAuthorize("hasRole ('ADMIN')")
    @DeleteMapping("/eliminar_publicacion/{idPublicacion}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable Long idPublicacion){
        adminService.elimiarPublicacion(idPublicacion);
        return ResponseEntity.ok("La publicación ha sido eliminada.");
    }

}
