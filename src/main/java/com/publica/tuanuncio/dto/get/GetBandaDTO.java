package com.publica.tuanuncio.dto.get;

import com.publica.tuanuncio.dto.PublicacionDTO;
import com.publica.tuanuncio.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBandaDTO {

    private UsuarioDTO usuario;
    private String nombreBanda;
    private String provincia;
    private String localidad;
    private String linkRedSocial;
    private List<PublicacionDTO> publicaciones;



}
