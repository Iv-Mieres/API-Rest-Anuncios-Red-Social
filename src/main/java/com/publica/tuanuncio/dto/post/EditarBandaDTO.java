package com.publica.tuanuncio.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditarBandaDTO {

	
	private CrearUsuarioDTO usuario;
	@NotNull(message = "No puede estar vacío")
	@Size(min = 4, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	private String provincia;
	@NotNull(message = "No puede estar vacío")
	@Size(min = 4, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	private String localidad;
	@NotNull(message = "No puede estar vacío")
	private String linkRedSocial;
	@NotNull(message = "No puede estar vacio")
	@Size(min= 3, message = "Debe contener un minimo de 3 caracteres")
	private String nombreBanda;

}
