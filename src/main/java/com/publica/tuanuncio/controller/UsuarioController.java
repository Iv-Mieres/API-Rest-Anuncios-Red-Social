package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.post.PostLoginDTO;
import com.publica.tuanuncio.dto.post.PostUsuarioDTO;
import com.publica.tuanuncio.service.IUserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private IUserAdminService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    //CREAR USUARIO CON ROLE_USER
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@Valid @RequestBody PostUsuarioDTO usuario) {
        usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Su usuario ha sido creado con exito!");
    }

    // LOGUEARSE
    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody PostLoginDTO login) {
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(login.getUsername(),
                                                                login.getPassword())));
        return ResponseEntity.ok("LOGIN: Ha iniciado sesion correctamente!");
    }

}
