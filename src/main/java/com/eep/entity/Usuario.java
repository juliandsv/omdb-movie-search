package com.eep.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.processing.Pattern;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;






@Entity
@Table(name ="usuarios")
@Data

@AllArgsConstructor
public class Usuario {
	
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Id
	private String email;
	
	private String name;
	
	private String apellido1;
	
	private String apellido2;
	
	private String password;
	@Column(nullable = false)
    
	private String telefono;
	
	
	@Column(nullable = false)
	@NotNull
	private LocalDate fechaNacimiento;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Peliculas> peliculasGuardadas;
	
	public List<Peliculas> getPeliculasGuardadas() {
        return peliculasGuardadas;
    }

    public void setPeliculasGuardadas(List<Peliculas> peliculasGuardadas) {
        this.peliculasGuardadas = peliculasGuardadas;
    }
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "peliculas_compartidas",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "pelicula_id")
       
    )
    
    private List<Peliculas> peliculasCompartidas = new ArrayList<>();

    public List<Peliculas> getPeliculasCompartidas() {
        return peliculasCompartidas;
    }

    public void setPeliculasCompartidas(List<Peliculas> peliculasCompartidas) {
        this.peliculasCompartidas = peliculasCompartidas;
    }
	
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Usuario() {}

	public Usuario(String email, String name, String apellido1, String apellido2, String password,
			LocalDate fechaNacimiento, String telefono,  List<Peliculas> peliculasGuardadas ) {
		super();
		this.email = email;
		this.name = name;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono= telefono;
		this.peliculasGuardadas = peliculasGuardadas;
	}
	
	

	
	
	
	
	

}
