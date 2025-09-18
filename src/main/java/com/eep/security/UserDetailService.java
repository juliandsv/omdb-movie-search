package com.eep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eep.entity.Usuario;
import com.eep.repositories.UsuarioRepository;

@Service
@Primary
public class UserDetailService implements UserDetailsService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 Usuario usuario = usuarioRepository.findById(email).orElseThrow(() -> 
         new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));
		 
		 return User.builder()
	                .username(usuario.getEmail())
	                .password(usuario.getPassword()) 
	                .authorities("USER") 
	                .build();
	}

}
