package de.tauiotamy.templates.springboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.tauiotamy.templates.springboot.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findByTitle(String title);
}