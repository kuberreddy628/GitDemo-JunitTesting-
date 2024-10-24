package com.junit.demo.service;

import java.util.List;

import com.junit.demo.entity.Product;

public interface ProductService {

	Product saveProduct(Product product);
	Product updateProduct(Integer id, Product product);
	List<Product> fetchAllProducts();
	Product fetchProductById(Integer id);
	String deleteProduct(Integer id);
}
