package com.example.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.bean.Products;

public interface ProductsRepository extends MongoRepository<Products, String> {
	//Products findBy_id(ObjectId _id);
}
