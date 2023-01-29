package com.publica.tuanuncio.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUsuarioDTO {

    private GetBandaDTO banda;
    private GetMusicoDTO musico;

}
