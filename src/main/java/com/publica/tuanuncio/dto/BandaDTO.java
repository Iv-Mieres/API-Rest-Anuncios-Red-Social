package com.publica.tuanuncio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
