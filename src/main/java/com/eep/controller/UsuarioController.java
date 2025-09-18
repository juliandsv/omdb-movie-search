package com.eep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eep.entity.Usuario;
import com.eep.service.UsuarioService;

@Controller
@RequestMapping
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String LoginPage() {
		
		return "login";
		
	}
	 @PostMapping
	 public String loginPost(@RequestParam("email") String email, @RequestParam("password") String password) {
	        // Lógica para autenticar el usuario
	        return "redirect:/Buscar";  // Redirige a /buscar después del login
	    }
	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public String RegistroPage() {
		
		return "registro";
		
	}
	
	
	@PostMapping(value="/registro")
	public String RegistroPage(@ModelAttribute Usuario usuario) {
		usuarioService.registrarUsuario(usuario);
		return "redirect:/login"; 
	}

}
