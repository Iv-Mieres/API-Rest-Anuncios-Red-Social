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
@EqualsAndHashCode(callSuper= true)
@Entity(name = "bandas")
@AttributeOverride(name="idPerfil", column=@Column(name="idBanda"))
public class Banda extends PerfilUsuario{
	@NotNull(message = "No puede estar vacio")
	@Size(min= 3, message = "Debe contener un minimo de 3 caracteres")
	private String nombreBanda;
	@OneToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", unique = true )
	@JsonIgnoreProperties(value = "perfilBanda")
	private Usuario unUsuario;
	@OneToMany(mappedBy = "banda")
	@Cascade(CascadeType.ALL)
	private List<Publicacion> publicacionesBandas;

}
