package com.publica.tuanuncio.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class PerfilUsuario{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idPerfil;
	@NotNull(message = "No puede estar vacío")
	@Size(min = 4, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	private String provincia;
	@NotNull(message = "No puede estar vacío")
	@Size(min = 4, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	private String localidad;
	@NotNull(message = "No puede estar vacío")
	private String linkRedSocial;
}
