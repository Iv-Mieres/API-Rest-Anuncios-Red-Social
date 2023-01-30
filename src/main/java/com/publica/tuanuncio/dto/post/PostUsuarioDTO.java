package com.publica.tuanuncio.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUsuarioDTO {

	@NotNull(message = "No puede estar vacio")
	@Size(min = 4, max = 50, message = "Debe contener entre 4 y 50 caracteres")
	@Column(unique = true)
	private String username;
	@NotNull(message = "No puede estar vacio")
	@Size(min = 6, message = "Debe contener un minimo de 6 caracteres")
	private String password;
	@NotNull(message = "No puede estar vacio")
	@Email(message = "Ingrese un Email válido")
	@NotBlank(message = "No puede estar vacio")
	@Column(unique = true)
	private String email;
	@NotNull(message = "No puede estar vacio")
	@Size(min = 6, message = "Debe contener un minimo de 6 caracteres")
	private String repeatPassword;
	@NotNull(message = "No puede estar vacio")
	@Email(message = "Ingrese un Email válido")
	@NotBlank(message = "No puede estar vacio")
	private String repeatEmail;
	
}
