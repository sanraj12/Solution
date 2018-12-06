package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.Products;
import com.example.demo.error.ProductNotFoundException;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.serviceImpl.ProductServiceImpl;

@RestController
@RequestMapping(value = "/product")
public class ProductService {

	@Autowired
	ProductServiceImpl ProductService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Products> getAllProducts() {
		return ProductService.getAllProducts();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Products InsertNewProduct(@RequestBody Products product) {
		return ProductService.InsertNewProduct(product);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Products> getProductById(@PathVariable("id") String id) {
		Optional<Products> optional = ProductService.getProductById(id);

		if (!optional.isPresent())
			throw new ProductNotFoundException(id);
		return ProductService.getProductById(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void modifyProductById(@PathVariable("id") String id, @Valid @RequestBody Products produscts) {
		produscts.setId(id);
		ProductService.modifyProductById(id, produscts);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String id) {
		ProductService.deleteProduct(id);
	}
}
