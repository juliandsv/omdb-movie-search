package com.eep.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eep.entity.Peliculas;
import com.eep.entity.Usuario;
import com.eep.model.PeliculaDto;

import com.eep.repositories.PeliculaRepository;
import com.eep.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class PeliculaServiceImp implements PeliculaService {
	

	
	 @Value("${omdb.api.base:https://www.omdbapi.com/}")
	    private String omdbBase;

	    @Value("${omdb.api.key:}")
	    private String omdbApiKey;
	    
	    private String buildSearchUrl(String query, int page) {
	        return String.format("%s?apikey=%s&s=%s&type=movie&page=%d",
	                omdbBase,
	                omdbApiKey,
	                URLEncoder.encode(query, StandardCharsets.UTF_8),
	                page);
	    }

	    private String buildDetailUrl(String imdbId) {
	        return String.format("%s?apikey=%s&i=%s&plot=full",
	                omdbBase,
	                omdbApiKey,
	                imdbId);
	    }
    
   
	    @Autowired
	    private PeliculaRepository peliculaRepository; 
	    
	    @Autowired
	    private UsuarioRepository usuarioRepository;

	    @Override
	    public List<Peliculas> findAll() {
	        List<Peliculas> peliculasList = new ArrayList<>();
	        peliculaRepository.findAll().forEach(peliculasList::add);
	        return peliculasList;
	    }
	   
	    
	    @Override
	    public List<Peliculas> BuscarPeliculas(String title) {
	        try {
	            if (omdbApiKey == null || omdbApiKey.isBlank()) {
	                throw new IllegalStateException("OMDb API key no configurada. Define OMDB_API_KEY en tu entorno.");
	            }

	            // Construye URL con helpers y codificación
	            String url = buildSearchUrl(title, 1);
	            System.out.println("URL construida: " + url);

	            RestTemplate restTemplate = new RestTemplate();
	            String response = restTemplate.getForObject(url, String.class);
	            System.out.println("Respuesta de la API: " + response);

	            ObjectMapper objectMapper = new ObjectMapper()
	                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	            Map<String, Object> responseData = objectMapper.readValue(response, Map.class);

	            if (responseData == null || responseData.get("Search") == null) {
	                System.out.println("No se encontraron películas.");
	                return new ArrayList<>();
	            }

	            List<Map<String, Object>> peliculasData = (List<Map<String, Object>>) responseData.get("Search");
	            List<Peliculas> peliculasList = new ArrayList<>();

	            for (Map<String, Object> peliculaData : peliculasData) {
	                // Estos campos (Director, Plot, Actors, Runtime) NO vienen en ?s=...
	                // Si quieres rellenarlos, haz luego un getById (buildDetailUrl) por cada imdbID.
	                Peliculas pelicula = new Peliculas(
	                        (String) peliculaData.get("imdbID"),
	                        (String) peliculaData.get("Title"),
	                        null,                           // Director (vendrá null en búsqueda)
	                        (String) peliculaData.get("Year"),
	                        null,                           // Runtime
	                        (String) peliculaData.get("Poster"),
	                        (String) peliculaData.get("Type"),
	                        null,                           // Plot
	                        null                            // Actors
	                );
	                peliculasList.add(pelicula);
	            }

	            return peliculasList;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ArrayList<>();
	        }
	    }






	    private List<Peliculas> convertirDtoAEntidad(PeliculaDto peliculaDto) {
	        List<Peliculas> peliculas = new ArrayList<>();
	        
	        // Verificar si el DTO es válido y si tiene datos en la lista de resultados
	        if (peliculaDto != null && peliculaDto.getSearch() != null) {
	            for (PeliculaDto.Pelicula dtoPelicula : peliculaDto.getSearch()) {
	                // Imprimir la información para depuración
	                System.out.println("ImdbID: " + dtoPelicula.getimdbID());
	                
	                // Crear un nuevo objeto Peliculas para cada DTO
	                Peliculas pelicula = new Peliculas();
	                // Asignar los valores del DTO a la entidad Peliculas
	                pelicula.setImdbID(dtoPelicula.getimdbID());  // Asegúrate de que el nombre de los métodos sea correcto
	                pelicula.setTitle(dtoPelicula.getTitle());
	                pelicula.setDirector(dtoPelicula.getDirector());
	                pelicula.setYear(dtoPelicula.getYear());
	                pelicula.setRuntime(dtoPelicula.getRuntime());
	                pelicula.setPoster(dtoPelicula.getPoster());
	                pelicula.setPuntuacion(dtoPelicula.getPuntuacion());

	                // Agregar la película convertida a la lista
	                peliculas.add(pelicula);
	                System.out.println("ImdbID   : " + pelicula.getImdbID());
	                
	                
	            }
	        }
	        
	        // Retornar la lista de películas convertidas
	        return peliculas;
	    }


	   
	    @Override
	    @Transactional
	    public void guardarPelicula(PeliculaDto.Pelicula peliculaDto, String emailUsuario) {
	        // Crear la entidad Peliculas
	        Peliculas pelicula = new Peliculas();
	        
	        // Mapear los datos del DTO a la entidad
	        pelicula.setImdbID(peliculaDto.getimdbID());
	        System.out.println("el imdbID es " + pelicula.getImdbID());
	        pelicula.setTitle(peliculaDto.getTitle());
	        pelicula.setDirector(peliculaDto.getDirector());
	        pelicula.setYear(peliculaDto.getYear());
	        pelicula.setRuntime(peliculaDto.getRuntime());
	        pelicula.setPoster(peliculaDto.getPoster());
	        pelicula.setPuntuacion(peliculaDto.getPuntuacion());
	        
	        
	        // Obtener el usuario desde el repositorio por el email
	        Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
	        
	        // Verificar si el usuario existe
	        if (usuario != null) {
	            // Asignar el usuario a la película
	            pelicula.setUsuario(usuario);
	            
	            // Agregar la película a la lista de peliculasGuardadas del usuario
	            usuario.getPeliculasGuardadas().add(pelicula);
	            
	            // Guardar la película en la base de datos
	            peliculaRepository.save(pelicula);
	            System.out.println("la puntuacion es: " + pelicula.getPuntuacion());
	            
	            // Guardar el usuario con la nueva película en su lista
	            usuarioRepository.save(usuario);
	        } else {
	            // Si no se encuentra el usuario, manejar el error
	            throw new RuntimeException("Usuario con email " + emailUsuario + " no encontrado.");
	        }
	    }


		@Override
		public List<Peliculas> obtenerPeliculasGuardadasPorUsuario(String email) {
			// TODO Auto-generated method stub
			 return peliculaRepository.findByUsuarioEmail(email);
		}
	    
		 @Override
			public void eliminarPelicula(Long id) {
			 Peliculas pelicula = peliculaRepository.findById(id).orElse(null);

		        if (pelicula != null) {
		            // Obtener los usuarios que han compartido esta película
		            List<Usuario> usuarios = usuarioRepository.findUsuariosByPeliculaId(id);

		            // Eliminar la película de la lista de peliculasCompartidas de cada usuario
		            for (Usuario usuario : usuarios) {
		                usuario.getPeliculasCompartidas().remove(pelicula);  // Eliminar la película de la lista de cada usuario
		            }

		            // Eliminar la película de la base de datos
		            peliculaRepository.deleteById(id);
			}
		 }
		 public Peliculas findById(Long id) {
				// TODO Auto-generated method stub
				 return peliculaRepository.findById(id).orElse(null);
		 }
		 
		 
		 
		 
		 @Override
		 public void enviarPeliculaAUsuario(Long peliculaId, String emailUsuario) {
		     // Obtener la película por su ID
		     Peliculas pelicula = peliculaRepository.findById(peliculaId).orElseThrow(() -> new RuntimeException("Película no encontrada"));

		     // Obtener el usuario por su email
		     Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
		     if (usuario == null) {
		         throw new RuntimeException("Usuario con email " + emailUsuario + " no encontrado.");
		     }

		   
		     pelicula.setUsuario(usuario);
		     peliculaRepository.save(pelicula);

		     
		 }
		@Override
		public List<Peliculas> obtenerPeliculasCompartidasPorUsuario(String emailUsuario) {
			// TODO Auto-generated method stub
			return peliculaRepository.findPeliculasCompartidasPorEmail(emailUsuario);
		}
		@Override
		public List<Peliculas> findByUsuarioCompartido_Email(String email) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Peliculas obtenerPeliculaPorId(Long id) {
			// TODO Auto-generated method stub
			  return peliculaRepository.findById(id).orElse(null); 
		}


		@Override
		public Peliculas obtenerDetallesPorImdbId(String imdbId) {
		    try {
		        // Crear la URL para hacer la búsqueda por imdbID
		    	if (omdbApiKey == null || omdbApiKey.isBlank()) {
		    	    throw new IllegalStateException("OMDb API key no configurada. Define OMDB_API_KEY en tu entorno.");
		    	}
		    	String url = buildDetailUrl(imdbId);

		        // Realizar la solicitud a la API de OMDb
		        RestTemplate restTemplate = new RestTemplate();
		        String response = restTemplate.getForObject(url, String.class);

		        // Usar ObjectMapper para mapear la respuesta a un objeto Peliculas
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		        // Deserializar la respuesta de la API en un objeto de tipo Peliculas
		        Map<String, Object> responseData = objectMapper.readValue(response, Map.class);

		        // Verificar si la respuesta tiene datos válidos
		        if (responseData != null && "True".equals(responseData.get("Response"))) {
		            // Crear una instancia de Peliculas con los datos obtenidos
		            Peliculas pelicula = new Peliculas(
		                    (String) responseData.get("imdbID"),
		                    (String) responseData.get("Title"),
		                    (String) responseData.get("Director"),
		                    (String) responseData.get("Year"),
		                    (String) responseData.get("Runtime"),
		                    (String) responseData.get("Poster"),
		                    (String) responseData.get("Type"),
		                    (String) responseData.get("Plot"),
		                    (String) responseData.get("Actors")
		            );

		            // Aquí puedes agregar más campos según los datos que quieras devolver

		            return pelicula;
		        } else {
		            System.out.println("No se encontró la película con el imdbID: " + imdbId);
		            return null;  // O puedes lanzar una excepción si prefieres
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return null;  // Manejo de errores
		    }
		}


		
		
	
		 
		
	
	
	

		
	

	

	
	
}
