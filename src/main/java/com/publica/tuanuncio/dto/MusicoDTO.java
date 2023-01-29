package com.publica.tuanuncio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicoDTO {

    private UsuarioDTO usuario;
    private String nombre;
    private String apellido;
    private String provincia;
    private String localidad;
    private String instrumento;
    private String linkRedSocial;
}
