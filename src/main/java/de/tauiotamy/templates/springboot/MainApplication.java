package de.tauiotamy.templates.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 * Logger.
	 */
	private static Logger LOG = LoggerFactory.getLogger(MainApplication.class);

	/**
	 * Einstiegsmethode.
	 * 
	 * @param args Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		LOG.info("Starte Anwendung ...");
		SpringApplication.run(MainApplication.class, args);
	}
}
