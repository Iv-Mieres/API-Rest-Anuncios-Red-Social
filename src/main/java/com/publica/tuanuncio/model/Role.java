package com.publica.tuanuncio.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long idRole;
	@NotNull(message = "No puede estar vacío")
	@Size(min = 4, max = 10, message = "Debe contener entre 4 y 10 caracteres")
	public String roleAuthority;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnoreProperties(value = "roles")
	private List<Usuario> listaUsuarios;
	
}
