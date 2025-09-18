


package com.eep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
@Validated
public class OmdbMovieSearchApplication  {

	public static void main(String[] args) {
		SpringApplication.run(OmdbMovieSearchApplication.class, args);
	}
	 
	  
}
