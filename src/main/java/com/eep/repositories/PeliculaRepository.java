package com.eep.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eep.entity.Peliculas;

public interface PeliculaRepository extends JpaRepository<Peliculas, Long> {
    List<Peliculas> findByUsuarioEmail(String email);  // Método para obtener las películas por el email del usuario
    
   
    
    @Query("SELECT p FROM Peliculas p WHERE p.usuario.email != :email")
    List<Peliculas> findPeliculasCompartidasPorEmail(@Param("email") String emailUsuario);

   
}



 