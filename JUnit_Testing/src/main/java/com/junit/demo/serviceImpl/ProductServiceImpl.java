package com.junit.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junit.demo.entity.Product;
import com.junit.demo.repository.ProductRepository;
import com.junit.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	boolean flag;

	@Override
	public Product saveProduct(Product product) {

		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Integer id, Product product) {

		Product product1 = productRepository.findById(id).get();
		product1.setDepartment(product.getDepartment());
		product1.setName(product.getName());
		product1.setPrice(product.getPrice());

		return productRepository.save(product1);
	}

	@Override
	public List<Product> fetchAllProducts() {

		return productRepository.findAll();
	}

	@Override
	public Product fetchProductById(Integer id) {
		if (id != 0 && id != null) {
			flag = productRepository.existsById(id);
		}
		if (flag) {
			return productRepository.findById(id).get();
		}
		return null;
	}

	@Override
	public String deleteProduct(Integer id) {
		if (id != 0 && id != null) {
			flag = productRepository.existsById(id);
		}
		if (flag) {
			productRepository.deleteById(id);
		}
		return "product deleted";
	}
}
