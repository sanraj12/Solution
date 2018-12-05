package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.bean.Products;
import com.example.demo.repository.ProductsRepository;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductsRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Products> getAllProducts() {
		return repository.findAll();
	}

	public Products InsertNewProduct(Products product) {
		mongoTemplate.save(product);
		return product;
	}

	public Optional<Products> getProductById(String id) {
		return repository.findById(id);
	}

	public void modifyProductById(String id, Products produscts) {
		produscts.setId(id);
		repository.save(produscts);
	}

	public void deleteProduct(String id) {
		repository.delete(repository.findById(id).get());
	}
}
