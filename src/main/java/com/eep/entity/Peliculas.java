package com.eep.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="peliculas")
public class Peliculas {

 

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto generará el ID automáticamente
    @Column(name = "id")
    private Long id;
    
    @Column(name = "imdb_id", columnDefinition = "VARCHAR(255)")
    private String imdbID;

    @Column(name="titulo")
    private String Title;

    @Column(name = "director")
    private String Director;

    @Column(name = "anio")
    private String Year;

    @Column(name = "duracion")
    private String Runtime;

    @Column(name = "poster")
    private String Poster;
    
    @Column(name = "type")  // 
    private String Type;  // 

    @Column(name = "plot")  
    private String Plot;  

    @Column(name = "actors")  
    private String Actors;  
    
    @Column(name = "puntuacion")
    @Min(1) 
    @Max(5)
    private Integer puntuacion;

    @ManyToOne
    @JoinColumn(name = "usuario_email", referencedColumnName = "email") // Relación con email del usuario
    private Usuario usuario;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getType() {  
        return Type;
    }

    public void setType(String type) {  
        this.Type = type;
    }

    public String getPlot() {  
        return Plot;
    }

    public void setPlot(String plot) {  
        this.Plot = plot;
    }

    public String getActors() {  
        return Actors;
    }

    public void setActors(String actors) {  
        this.Actors = actors;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Integer getPuntuacion() {
 		return puntuacion;
 	}

 	public void setPuntuacion(Integer puntuacion) {
 		this.puntuacion = puntuacion;
 	}

   
    public Peliculas(String imdbID, String title, String director, String year, String runtime, String poster, String type, String plot, String actors) {
        this.imdbID = imdbID;
        this.Title = title;
        this.Director = director;
        this.Year = year;
        this.Runtime = runtime;
        this.Poster = poster;
        this.Type = type; 
        this.Plot = plot; 
        this.Actors = actors;  
    }

    
    public Peliculas() {
    }

    @Override
    public String toString() {
        return "Peliculas [imdbID=" + imdbID + ", title=" + Title + ", Director=" + Director + ", Year=" + Year
                + ", Runtime=" + Runtime + ", Poster=" + Poster + ", Type=" + Type + ", Plot=" + Plot + ", Actors=" + Actors + "]";
    }
}
