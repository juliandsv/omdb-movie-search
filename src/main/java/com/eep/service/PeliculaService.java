package com.eep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eep.entity.Peliculas;
import com.eep.model.PeliculaDto;

@Service
public interface PeliculaService {
	
	 public List<Peliculas> findAll();
	
	 List<Peliculas> BuscarPeliculas(String titulo); 
	 
	 Peliculas obtenerDetallesPorImdbId(String imdbId);
	 
	 
	 void guardarPelicula(PeliculaDto.Pelicula pelicula, String emailUsuario);
	 
	 List<Peliculas> obtenerPeliculasGuardadasPorUsuario(String email);
	 
	 void eliminarPelicula(Long id);
	 
	 void enviarPeliculaAUsuario(Long peliculaId, String emailUsuario);
	 
	 public Peliculas findById(Long id);
	 
	 List<Peliculas> obtenerPeliculasCompartidasPorUsuario(String emailUsuario);
	 
	 List<Peliculas> findByUsuarioCompartido_Email(String email);

	public Peliculas obtenerPeliculaPorId(Long id);
	 
	


		    
		}
	
	


