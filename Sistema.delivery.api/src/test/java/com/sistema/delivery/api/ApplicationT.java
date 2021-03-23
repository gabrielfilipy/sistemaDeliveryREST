package com.sistema.delivery.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintViolationException;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.repository.CozinhaRepository;
import com.sistema.delivery.api.domain.service.CozinhaRepositoryService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationT {
	
	@LocalServerPort
	private Integer porta;
	
	@Autowired
	private Flyway flyway;
	
	@Before
	public void setUp()
	{
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.basePath = "/cozinha/";
		RestAssured.port = porta;
		
		flyway.migrate();
	}
	
	@Test
	public void testarStatus200ParaQuandoListarCozinha()
	{
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testarDeveConter4Cozinhas()
	{
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then() 
			.body("", Matchers.hasSize(2))
			.body("nome", Matchers.hasItems("Norte-Americana", "Manauara"));
	}
	
	@Test
	public void testeRetornaStatus200QuandoCadastrarCozinha()
	{
		RestAssured.given()
			.body("{ \"nome\":  \"chinesa\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.OK.value()); 
	}
	
}
