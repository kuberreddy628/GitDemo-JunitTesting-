package com.junit.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junit.demo.entity.Product;

public interface TestRepository extends JpaRepository<Product, Integer> {

}
