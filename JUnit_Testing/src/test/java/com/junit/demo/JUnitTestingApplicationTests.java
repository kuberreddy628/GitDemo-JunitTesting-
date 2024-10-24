package com.junit.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hibernate.sql.ast.SqlAstJoinType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.internal.function.text.Concatenate;
import com.junit.demo.entity.Product;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JUnitTestingApplicationTests {

	@LocalServerPort
	private int port;

	private static String baseURI = "http://localhost:";

	@Autowired
	private static RestTemplate restTemplate;

	@Autowired
	TestRepository repository;

	@BeforeAll
	public static void getRestTemplate() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void getURL() {
		baseURI = baseURI.concat(String.valueOf(port)).concat("/product");
	}

	@Test
	public void addProduct() {
		Product product = new Product("Pen", "10", "stationary");
		Product response = restTemplate.postForEntity(baseURI + "/save", product, Product.class).getBody();
		assertEquals("Pen", response.getName());
		// assertEquals(3, repository.findAll().size());
	}

	@Test
	@Sql(statements = "insert into product(id,name,price,department) values(402,'Pencil','5','stationary')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from product where id = 402", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getAllProducts() {

		List<Product> products = restTemplate.getForObject(baseURI + "/allproducts", List.class);
		assertEquals(6, products.size());
		assertEquals(6, repository.findAll().size());
	}

	@Test
	public void getById() {

		Product products = restTemplate.getForObject(baseURI + "/get/{id}", Product.class, 2);

		assertAll(() -> assertEquals("Book", products.getName()), () -> assertNotNull(products),
				() -> assertEquals(2, products.getId()));
	}

	@Test
	public void updateProducts() {
		Product product = new Product("Marker", "15", "stationary");
		restTemplate.put(baseURI + "/update/{id}", product, 352);
		Product products = restTemplate.getForObject(baseURI + "/get/{id}", Product.class, 352);
		assertEquals("Marker", products.getName());
		assertEquals(5, repository.findAll().size());
	}

	@Test
	public void deleteProducts() {

		restTemplate.delete(baseURI + "/delete/{id}", 352);
		Product products = restTemplate.getForObject(baseURI + "/get/{id}", Product.class, 352);
	//	assertEquals("Marker", products.getName());       //if we add this line, test case should file 
		assertEquals(4, repository.findAll().size());            

	}
}
