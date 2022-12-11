package de.tauiotamy.templates.springboot;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tauiotamy.templates.springboot.entity.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootBootstrapLiveTest {

	private static final String API_ROOT = "/api/books";

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() throws Exception {
		RestAssured.port = port;
	}

	@Test
	public void whenGetAllBooks_thenOK() {
		final Response response = RestAssured.get(API_ROOT);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void whenGetBooksByTitle_thenOK() {
		final Book book = createRandomBook();
		createBookAsUri(book);

		final Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertTrue(response.as(List.class).size() > 0);
	}

	@Test
	public void whenGetCreatedBookById_thenOK() {
		final Book book = createRandomBook();
		final String location = createBookAsUri(book);

		final Response response = RestAssured.get(location);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertEquals(book.getTitle(), response.jsonPath().get("title"));
	}

	@Test
	public void whenGetNotExistBookById_thenNotFound() {
		final Response response = RestAssured
				.get(API_ROOT + "/" + org.apache.commons.lang3.RandomStringUtils.randomNumeric(4));
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	// POST
	@Test
	public void whenCreateNewBook_thenCreated() {
		final Book book = createRandomBook();

		final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book)
				.post(API_ROOT);
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	}

	@Test
	public void whenInvalidBook_thenError() {
		final Book book = createRandomBook();
		book.setAuthor(null);

		final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book)
				.post(API_ROOT);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	@Test
	public void whenUpdateCreatedBook_thenUpdated() {
		final Book book = createRandomBook();
		final String location = createBookAsUri(book);

		book.setId(Long.parseLong(location.split("api/books/")[1]));
		book.setAuthor("newAuthor");
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).put(location);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		response = RestAssured.get(location);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assertions.assertEquals("newAuthor", response.jsonPath().get("author"));

	}

	@Test
	public void whenDeleteCreatedBook_thenOk() {
		final Book book = createRandomBook();
		final String location = createBookAsUri(book);

		Response response = RestAssured.delete(location);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		response = RestAssured.get(location);
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	// ===============================

	private Book createRandomBook() {
		final Book book = new Book();
		book.setTitle(org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10));
		book.setAuthor(org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(15));
		return book;
	}

	private String createBookAsUri(Book book) {
		final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book)
				.post(API_ROOT);
		return API_ROOT + "/" + response.jsonPath().get("id");
	}
}
