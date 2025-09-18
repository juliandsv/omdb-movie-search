package com.eep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.eep.entity.Usuario;
import com.eep.repositories.UsuarioRepository;


public interface UsuarioService {
	  

	
	List<Usuario>findAll();
	
	public void registrarUsuario(Usuario usuario);
	
	Optional<Usuario> findByEmail(String email);  // Buscar un usuario por correo electrónico
	    
	boolean existsByEmail(String email);  // Verificar si un usuario existe por correo electrónico

	Usuario save(Usuario usuario);


	    


}
