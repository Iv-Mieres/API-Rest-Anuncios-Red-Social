package com.publica.tuanuncio.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "musicos")
@AttributeOverride(name="idPerfil", column=@Column(name="idMusico"))
public class Musico extends PerfilUsuario{
	@NotNull(message = "Debe contener un nombre")
	@Size(min = 4, message = "Debe contener un minimo de 4 caracteres")
	private String nombre;
	@NotNull(message = "Debe contener un apellido")
	@Size(min = 4, message = "Debe contener un minimo de 4 caracteres")
	private String apellido;
	@NotNull(message = "Debe contener un instrumento")
	@Size(min = 3, message = "Debe contener un minimo de 3 caracteres")
	private String instrumento;
	@OneToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", unique = true )
	@JsonIgnoreProperties(value = "perfilMusico")
	private Usuario unUsuario;
	@OneToMany(mappedBy = "musico")
	@Cascade(CascadeType.ALL)
	private List<Publicacion> publicacionesMusicos;

}
