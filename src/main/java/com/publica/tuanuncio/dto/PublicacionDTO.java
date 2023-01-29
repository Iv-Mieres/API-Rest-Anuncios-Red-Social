package com.publica.tuanuncio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    private Long idPublicacion;
    private String titulo;
    private LocalDate fechaPublicacion;
    private String generoMusical;
    private boolean remunerado;
    private String descripcion;
}
