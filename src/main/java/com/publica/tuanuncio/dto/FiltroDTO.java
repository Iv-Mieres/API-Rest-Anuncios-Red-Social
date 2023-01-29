package com.publica.tuanuncio.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroDTO {

	private String username;
	private String email;
	private String role;
	private LocalDate fechaPublicacion;
	private String generoMusical;
	private String provincia;
	private String localidad;
	private String instrumento;
	private String eliminado;

}
