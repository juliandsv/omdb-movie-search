package com.eep.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.eep.entity.Peliculas;
import com.eep.entity.Usuario;
import com.eep.model.PeliculaDto;
import com.eep.model.PeliculaDto.Pelicula;
import com.eep.repositories.PeliculaRepository;

import com.eep.service.PeliculaService;
import com.eep.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping()
public class PeliculaController {
	
	@Autowired
	 private PeliculaService peliculaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	 
	
	
    @GetMapping("/buscar")
    public String buscarFormulario(Model model) {
        model.addAttribute("titulo", "");
        return "Buscar"; 
    }

   
    @PostMapping("/buscar")
    public String buscar(@RequestParam("titulo") String titulo, Model model) {
        if (titulo == null || titulo.isEmpty()) {
            model.addAttribute("error", "El título no puede estar vacío.");
            return "Buscar"; 
        }
        
       
        List<Peliculas> peliculas = peliculaService.BuscarPeliculas(titulo);
        
        if (peliculas.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron películas con ese título.");
        }
        System.out.println(peliculas);
        model.addAttribute("peliculas", peliculas);
        System.out.println(peliculas);
        return "resultado"; // 
    }
    
    @PostMapping("/guardarPelicula")
    public String guardarPelicula(@ModelAttribute("pelicula") PeliculaDto.Pelicula peliculaDto, Model model) {
        // Obtener el email del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName(); // El email del usuario autenticado
        
        // Llamar al servicio para guardar la película y asociarla con el usuario
        peliculaService.guardarPelicula(peliculaDto, emailUsuario); // Pasamos el email al servicio
        
       
        model.addAttribute("mensaje", "¡Película guardada exitosamente!");

        
        return "Buscar"; 
    }
    @PostMapping("/eliminarPelicula")
    public String eliminarPelicula(@RequestParam("id") Long id, Model model) {
        peliculaService.eliminarPelicula(id);
        model.addAttribute("mensaje", "Película eliminada exitosamente.");
        return "redirect:/misPeliculas"; 
    }
    
    @GetMapping("/misPeliculas")
    public String verMisPeliculas(Model model, Principal principal) {
        // Obtener el email del usuario logueado
        String email = principal.getName();

        
        List<Peliculas> peliculasGuardadas = peliculaService.obtenerPeliculasGuardadasPorUsuario(email);

        
        model.addAttribute("peliculasGuardadas", peliculasGuardadas);

       
        return "misPeliculas";
    }
  
    @PostMapping("/mostrarDetallesYUsuarios")
    public String mostrarDetallesYUsuariosPost(@RequestParam Long idPelicula, Model model) {
        Peliculas pelicula = peliculaService.findById(idPelicula);

        if (pelicula != null) {
            List<Usuario> usuarios = usuarioService.findAll();

            model.addAttribute("pelicula", pelicula);
            model.addAttribute("usuarios", usuarios);

            return "enviarPeliculas";
        } else {
            return "error";
        }
    }
    
    @PostMapping("/enviarPeliculas/{id}/{email}")
    public String enviarPelicula(@PathVariable Long id, @PathVariable String email, Model model) {
        // Buscar la película y el usuario en la base de datos
        Peliculas pelicula = peliculaService.findById(id);
        Usuario usuario = usuarioService.findByEmail(email).orElse(null);

        if (pelicula != null && usuario != null) {
            // Asociar la película al usuario
            usuario.getPeliculasCompartidas().add(pelicula);
            usuarioService.save(usuario);

            model.addAttribute("mensaje", "Película enviada correctamente a " + usuario.getEmail());

            return "redirect:/misPeliculas"; // 
        } else {
            model.addAttribute("error", "No se pudo enviar la película.");
            return "error"; // 
        }
    }
    
    @RequestMapping("/peliculasCompartidas")
    public String obtenerPeliculasCompartidas(Model model, HttpServletRequest request) {
        System.out.println("Controlador alcanzado"); 
        String emailUsuario = obtenerEmailUsuarioLogueado(request);

        if (emailUsuario == null) {
            return "redirect:/login";
        }

        List<Peliculas> peliculasCompartidas = peliculaService.obtenerPeliculasCompartidasPorUsuario(emailUsuario);
        model.addAttribute("peliculasCompartidas", peliculasCompartidas);

        return "peliculasCompartidas"; 
    }


    
    private String obtenerEmailUsuarioLogueado(HttpServletRequest request) {
        
        return request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;
    }
    
    
    @GetMapping("/Detalle/{imdbId}")
    public String obtenerDetalle(@PathVariable String imdbId, Model model) {
       
        Peliculas pelicula = peliculaService.obtenerDetallesPorImdbId(imdbId);

        if (pelicula != null) {
            model.addAttribute("pelicula", pelicula);
            return "Detalle";  
        }

      
        model.addAttribute("error", "Película no encontrada.");
        return "error";  // Vista de error
    }
    

    
   





   
    

	
	   

}

