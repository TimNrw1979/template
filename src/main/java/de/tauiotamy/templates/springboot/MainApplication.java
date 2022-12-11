package de.tauiotamy.templates.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Einstiegsklasse.
 * 
 * @author timdo
 *
 */
@EnableJpaRepositories("de.tauiotamy.templates.springboot.repository")
@EntityScan("de.tauiotamy.templates.springboot.entity")
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
