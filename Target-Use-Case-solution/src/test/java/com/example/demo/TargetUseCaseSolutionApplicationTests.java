package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.bean.Price;
import com.example.demo.bean.Products;
import com.example.demo.service.ProductService;
import com.example.demo.serviceImpl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(value = ProductService.class, secure = false)
public class TargetUseCaseSolutionApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProductServiceImpl productService;

	@Test
	public void getProductsList() throws Exception {
		String uri = "/product";

		Products products = new Products();
		products.setId("10");
		products.setItem("Retail Item");
		products.setDescription("For Home use");
		products.setPrice(new Price("100", "USD"));
		List<Products> list = new ArrayList<>();
		list.add(products);

		Mockito.when(productService.getAllProducts()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		int status = mvcResult.getResponse().getStatus();

		String str = "[{\"id\":\"10\",\"item\":\"Retail Item\",\"description\":\"For Home use\",\"price\":{\"value\":\"100\",\"currency\":\"USD\"}}]";

		JSONAssert.assertEquals(str, mvcResult.getResponse().getContentAsString(), false);
		System.out.println(str);
		assertEquals(200, status);

	}

	@Test
	public void InsertNewProduct() throws Exception {

		String uri = "/product/";
		Products products = new Products();
		products.setId("20");
		products.setItem("Retail Item");
		products.setDescription("For Home use");
		products.setPrice(new Price("100", "USD"));

		Mockito.when(productService.InsertNewProduct(Mockito.any(Products.class))).thenReturn(products);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(products));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		int status = mvcResult.getResponse().getStatus();

		assertEquals(200, status);

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
