package com.publica.tuanuncio.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.publica.tuanuncio.repository.IUserAdminRepository;

@Service
public class LoginService implements UserDetailsService {

	@Autowired
	private IUserAdminRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var usuario = userRepository.findByUsername(username);
		var compararUsername = usuario.stream().anyMatch(u -> !u.getUsername().equals(username));

		//si el usuario está vacío ó su username no coincide con el username ingresado lanza una exception
		if (usuario.isEmpty() || compararUsername) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		//guarda los datos del usuario logueado en un objeto de tipo HttpSession
		var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		session.setAttribute("usersession", usuario.get());
		return usuario.get();
	}

}
