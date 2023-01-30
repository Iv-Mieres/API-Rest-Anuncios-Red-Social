package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.model.Role;
import com.publica.tuanuncio.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    //CREAR ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear_role")
    @Operation(summary = "Crea un role")
    public ResponseEntity<String> crearRole(@Valid @RequestBody Role role){
        roleService.crearRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body("El role se creó correctamente!");
    }

    //VER LISTA ROLES
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ver_roles")
    @Operation(summary = "Muestra roles")
    public ResponseEntity<List<Role>> roles(){
        return ResponseEntity.ok(roleService.roles());
    }

    //EDITAR ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar_role")
    @Operation(summary = "Edita un role por id.")
    public ResponseEntity<String> editarRole(@Valid @RequestBody Role role){
        roleService.editarRole(role);
        return ResponseEntity.ok("El role se editó correctamente!");
    }

    //ELIMINAR ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar_role/{idRole}")
    @Operation(summary = "Elimina un role por id.")
    public ResponseEntity<String> eliminarRole(Long idRole){
        roleService.eliminarRole(idRole);
        return ResponseEntity.ok("El role se eliminó correctamente!");
    }

}
