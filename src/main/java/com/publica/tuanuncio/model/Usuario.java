package com.publica.tuanuncio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuarios")
public  class Usuario implements Serializable, UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idUsuario;
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
	private String eliminado;
	@OneToOne(mappedBy = "unUsuario")
	@JsonIgnoreProperties(value = "unUsuario")
	private Banda perfilBanda;
	@OneToOne(mappedBy = "unUsuario")
	@JsonIgnoreProperties(value = "unUsuario")
	private Musico perfilMusico;
	
	@ManyToMany
	@JoinTable(name = "roles_usuario",
			joinColumns = @JoinColumn(name = "idUsuario"),
			inverseJoinColumns = @JoinColumn(name = "idRole"))
	@JsonIgnoreProperties(value = "listaUsuarios")
	private Set<Role> roles;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		var asignarAuthorities = new ArrayList<GrantedAuthority>();
		roles.forEach(r -> asignarAuthorities.add(new SimpleGrantedAuthority("ROLE_" + r.getRoleAuthority())));
		return asignarAuthorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return !this.eliminado.equalsIgnoreCase("true");
	}
}
