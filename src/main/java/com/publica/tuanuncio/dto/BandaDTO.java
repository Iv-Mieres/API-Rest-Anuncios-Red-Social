package com.publica.tuanuncio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandaDTO {

    private UsuarioDTO usuario;
    private String nombreBanda;
    private String provincia;
    private String localidad;
    private String linkRedSocial;
}
