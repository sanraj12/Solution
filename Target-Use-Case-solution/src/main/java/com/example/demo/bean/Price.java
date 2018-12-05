package com.example.demo.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Price {

	private String value;
	private String currency;
	
	public Price(String value,String currency )
	{
		this.value=value;
		this.currency=currency;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
