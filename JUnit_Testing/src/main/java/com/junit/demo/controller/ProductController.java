package com.junit.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junit.demo.entity.Product;
import com.junit.demo.repository.ProductRepository;
import com.junit.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.CREATED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id) {
		return new ResponseEntity<Product>(productService.fetchProductById(id), HttpStatus.OK);
	}

	@GetMapping("/allproducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<List<Product>>(productService.fetchAllProducts(), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
		return new ResponseEntity<Product>(productService.updateProduct(id, product), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id) {
		productService.deleteProduct(id);
		return new ResponseEntity<String>("Product deleted with id " + id, HttpStatus.OK);
	}
}
