# target-Use-Case-Solution
   This is a Java / Maven / Spring Boot application solution for given use case.
   
-About Solution and Reason behind choosing specific tech stack:

Spring Boot - It does lot of auto-configuration for us ,easy to create proejct and dependency also has embedded Tomcat Server also one of the main reason is I am using it for my current application.

MongoDB -  I pulled it from Docker. High performance, high availability,automatic scaling, simple to install and implement, MongoDB uses JSON or BSON documents to store data.

JDBC and In-memory Authentication with spring Security : For Enterprise Application we do it different way by using LDAP, SAML, OpenId,Authorization Server but for this Use case I have not consider these concepts implementation as it will take good amount of time.

Spring Rest : It was given in requirement that We have to write Rest API . I have worked on REST-API With Jersey and REST-API with Spring , I choose Spring because of Spring Boot selection. 
       
       

## Requirements
Spring Boot comes with embedded Tomcat Server.
For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven ](https://maven.apache.org)
- [MongoDb](https://www.mongodb.com/download-center).

I have pulled the MongoDb in Docker and then I have mapped with localhost:27017(Default port for mongoDB).
MongoDb Can be installed directlly into Local machine also.

No Tomcat installation is necessary.

Schema.sql will be exceuted by spring boot and  \src\main\resources
## Once MongoDb is installed and working perfectlly we will have to create database and collection.
     -use target_shops
      -create collection with name products.
        -insert few collection into database, Example below
                   - {
                    -  _id: 50
                      - item: "product_1"
                      - description: "product_1 desc",
                       - price: 	
                       - {
                        -    value:15,
                         -   currency: "USD"
    
                       - }
     
                   - }



## Running the application locally

There are several ways to run a Spring Boot application on our local machine. 
One way is to execute the `main` method in the  \src\main\java\com\example\demo\TargetUseCaseSolutionApplication.java

## Deploying the application

Application will be deployed on embedded Tomcat Server when run the application by opening TargetUseCaseSolutionApplication.java and Right Click-> Run As-> Java Application

## POM.xml 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.target</groupId>
	<artifactId>Target-Use-Case-solution</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>Target-Use-Case-solution</name>
	<description>solution for target</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.0.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>

## Controller/Service Class
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
import com.example.demo.repository.ProductsRepository;
import com.example.demo.serviceImpl.ProductServiceImpl;

@RestController
@RequestMapping(value = "/product")
public class ProductService {

	@Autowired
	ProductServiceImpl ProductService;
    
    // Spring boot default port is 808o for Tocat
    // Url: localhost:8080/product
    // This service will return all the products with thier details and price
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Products> getAllProducts() {
		return ProductService.getAllProducts();
	}

    //Url: localhost:8080/product
    //this will add new product with thier details and price
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Products InsertNewProduct(@RequestBody Products product) {
		return ProductService.InsertNewProduct(product);

	}

     //Url: localhost:8080/product/{id}
    //this will return  product by thier ID
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Products> getProductById(@PathVariable("id") String id) {
		return ProductService.getProductById(id);
	}

     //Url: localhost:8080/product/{id}
    //this will update existing product by thier ID
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void modifyProductById(@PathVariable("id") String id, @Valid @RequestBody Products produscts) {
		produscts.setId(id);
		ProductService.modifyProductById(id, produscts);
	}

     //Url: localhost:8080/product/{id}
    //this will delete existing product from record by thier ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String id) {
		ProductService.deleteProduct(id);
	}
}

