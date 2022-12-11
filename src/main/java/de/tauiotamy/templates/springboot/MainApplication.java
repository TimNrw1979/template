package de.tauiotamy.templates.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Einstiegsklasse.
 * 
 * @author timdo
 *
 */
@SpringBootApplication
public class MainApplication {

	/**
	 * Einstiegsmethode.
	 * 
	 * @param args Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
