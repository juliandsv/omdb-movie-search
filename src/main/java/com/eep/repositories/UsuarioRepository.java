package com.eep.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eep.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	
	Usuario findByEmail(String email);
	
	  @Query("SELECT u FROM Usuario u JOIN u.peliculasCompartidas p WHERE p.id = :peliculaId")
	    List<Usuario> findUsuariosByPeliculaId(@Param("peliculaId") Long Id);
	
	
	

}
