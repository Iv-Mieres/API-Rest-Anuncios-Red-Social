package com.publica.tuanuncio.dto.get;

import com.publica.tuanuncio.dto.BandaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublicacionBandaDTO {
    private Long idPublicacion;
    private String titulo;
    private LocalDate fechaPublicacion;
    private String generoMusical;
    private boolean remunerado;
    private String descripcion;
   private BandaDTO banda;

}
