package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetUsuarioDTO;
import com.publica.tuanuncio.dto.post.PostUsuarioDTO;
import com.publica.tuanuncio.model.Usuario;
import com.publica.tuanuncio.service.IUserAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "crea un usuario con role 'ADMIN'.")
    public ResponseEntity<String> crearAdmin(@Valid @RequestBody PostUsuarioDTO admin){
        adminService.crearAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Su usuario 'admin' ha sido creado con exito!");
    }

    //MUESTRA UN USUARIO POR ID
    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping("/ver_usuario/{idUsuario}")
    @Operation(summary = "Muestra los datos de un usuario por id")
    public ResponseEntity<Usuario> verUsuarioPorId(@PathVariable Long idUsuario){
        return ResponseEntity.ok(adminService.verUsuario(idUsuario));
    }

    //MUESTRA UNA LISTA DE USUARIOS PAGINADOS QUE PUEDE FILTRARSE POR ID, EMAIL Y/O USERNAME
    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping("/ver_lista_usuarios")
    @Operation(summary = "Muestra una lsta de usuarios paginados y/o filtrados. La utilización de los filtros es opcional.")
    public ResponseEntity<Page<GetUsuarioDTO>> getUsuarios(
            @Parameter(description = "Las busquedas solo se pueden filtrar por username, email, role y/o eliminado. " +
                    "Si se coloca un valor null se ignora el filtrado.")
            @RequestBody FiltroDTO filtro, Pageable pageable){
        return ResponseEntity.ok(adminService.listaUsuarios(filtro, pageable));
    }

    //EDITA LOS DATOS DE ADMINISTRADOR
    @PreAuthorize("hasRole ('ADMIN')")
    @PutMapping("/editar_admin")
    @Operation(summary = "Edita los datos del administrador.")
    public ResponseEntity<String> editarAdmin(@Valid @RequestBody PostUsuarioDTO usuarioDTO, HttpSession session){
        adminService.editarAdmin(session,usuarioDTO);
        return ResponseEntity.ok("Su usuario 'admin' ha sido editado correctamente!");
    }

    //ELIMINA UN USUARIO POR ID
    @PreAuthorize("hasRole ('ADMIN')")
    @DeleteMapping("/eliminar_usuario/{idUsuario}")
    @Operation(summary = "Elimina de forma lógica un usuario por id y borra de forma permanente el perfil y las publicaciones.")
    public ResponseEntity<String> eliminarUsuarios(@PathVariable Long idUsuario){
        adminService.eliminarUsuarioComoAdmin(idUsuario);
        return ResponseEntity.ok("El usuario con id " + idUsuario +" se eliminó correctamente!");
    }

    //ELIMINA UNA PUBLICACIÓN POR ID
    @PreAuthorize("hasRole ('ADMIN')")
    @DeleteMapping("/eliminar_publicacion/{idPublicacion}")
    @Operation(summary = "Elimina una publicación por id.")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable Long idPublicacion){
        adminService.elimiarPublicacion(idPublicacion);
        return ResponseEntity.ok("La publicación ha sido eliminada.");
    }

}
