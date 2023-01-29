package com.publica.tuanuncio.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "publicaciones")
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idPublicacion;
	@NotNull(message = "No puede estar vacio")
	@Size(min = 4, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	private String titulo;
	@NotNull(message = "No puede estar vacio")
	private LocalDate fechaPublicacion;
	@NotNull(message = "No puede estar vacio")
	@Size(min = 3, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	private String generoMusical;
	@ManyToOne
	@JoinColumn(name = "idBanda")
	@JsonIgnoreProperties(value = "publicacionesBandas")
	private Banda banda;
	@ManyToOne
	@JoinColumn(name = "idMusico")
	@JsonIgnoreProperties(value = "publicacionesMusicos")
	private Musico musico;
	@NotNull(message = "No puede estar vacio")
	@Lob
	@Size(min = 20, max = 500, message = "Debe contener entre 20 y 500 caracteres")
	private String descripcion;
	private boolean remunerado;
}
