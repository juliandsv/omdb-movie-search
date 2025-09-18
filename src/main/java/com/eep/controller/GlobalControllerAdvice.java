package com.eep.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.eep.entity.Usuario;
import com.eep.service.UsuarioService;

@ControllerAdvice
public class GlobalControllerAdvice {
	 	@Autowired
	    private UsuarioService usuarioService; 

	    @ModelAttribute
	    public void addUserDetails(Model model) {
	    	 
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        
	        
	        Optional<Usuario> optionalUsuario = usuarioService.findByEmail(email); // No es necesario envolverlo en Optional.ofNullable()

	        // Verificar si el usuario está presente
	        optionalUsuario.ifPresent(usuario -> {
	        	 model.addAttribute("nombreCompleto", usuario.getName() + " " + usuario.getApellido1());
	        });
	    
	    }

}
