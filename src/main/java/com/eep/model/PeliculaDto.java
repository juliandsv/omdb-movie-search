package com.eep.model;

import java.util.List;

import com.eep.entity.Peliculas;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeliculaDto {
	 @JsonProperty("Search")	
	  private List<Pelicula> Search;

	    // Getter y setter para Search
	    public List<Pelicula> getSearch() {
	        return Search;
	    }
	    public void setSearch(List<Pelicula> search) {
	        Search = search;
	    }
	
	    public static class Pelicula {
	    	public int getPuntuacion() {
				return puntuacion;
			}

			public void setPuntuacion(int puntuacion) {
				this.puntuacion = puntuacion;
			}

			public String getPlot() {
				return Plot;
			}

			public void setPlot(String plot) {
				Plot = plot;
			}

			public String getActors() {
				return Actors;
			}

			public void setActors(String actors) {
				Actors = actors;
			}

			public String getType() {
				return Type;
			}

			public void setType(String type) {
				Type = type;
			}

			@JsonProperty("Title")
	        private String Title;
	    	@JsonProperty("Year")
	        private String Year;
	    	@JsonProperty("Director")
	        private String Director;
	    	@JsonProperty("imdbID")
	        private String imdbID;
	    	@JsonProperty("Poster")
	        private String Poster;
	    	@JsonProperty("Type")
	    	private String Type;
	    	@JsonProperty("Runtime")
	        private String Runtime;
	    	@JsonProperty("Plot")
	    	private String Plot;
	    	@JsonProperty("Actors")
	        private String Actors;
	    	
	    	private int puntuacion;

	        public String getTitle() {
	            return Title;
	        }

	        public void setTitle(String title) {
	            Title = title;
	        }

	        public String getYear() {
	            return Year;
	        }

	        public void setYear(String year) {
	            Year = year;
	        }

	        public String getDirector() {
	            return Director;
	        }

	        public void setDirector(String director) {
	            Director = director;
	        }

	        public String getimdbID() {
	            return imdbID;
	        }

	        public void setimdbID(String imdbID) {
	            this.imdbID = imdbID;
	        }

	        public String getPoster() {
	            return Poster;
	        }

	        public void setPoster(String poster) {
	            Poster = poster;
	        }

	        public String getRuntime() {
	            return Runtime;
	        }

	        public void setRuntime(String runtime) {
	            Runtime = runtime;
	        }
	       

	        public Pelicula(String title, String year, String director, String imdbID, String poster, String type,
					String runtime, String plot, String actors, int puntuacion) {
				super();
				Title = title;
				Year = year;
				Director = director;
				this.imdbID = imdbID;
				Poster = poster;
				Type = type;
				Runtime = runtime;
				Plot = plot;
				Actors = actors;
				puntuacion = puntuacion;
			}

			public Pelicula() {}
	    }

}
