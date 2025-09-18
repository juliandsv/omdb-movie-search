package com.eep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eep.entity.Usuario;
import com.eep.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	
	
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Usuario> findAll() {
		
		
		return (List<Usuario>) repositorio.findAll();
	}

	

	@Override
	public Optional<Usuario> findByEmail(String email) {
		
		return repositorio.findById(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		
		return repositorio.existsById(email);
	}
    @Transactional
	@Override
	public void registrarUsuario(Usuario usuario) {
    	String encodedPassword = passwordEncoder.encode(usuario.getPassword()); 
		usuario.setPassword(encodedPassword); 
		
		repositorio.save(usuario);
		
	}
    @Override
	public Usuario save(Usuario usuario) {
    	
		
		return repositorio.save(usuario);
    }
    

	

	

}
