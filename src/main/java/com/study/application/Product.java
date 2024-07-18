package com.study.application;

import java.util.UUID;

import com.study.variables.Status;

public class Product implements ProductInterface {

	UUID id;
	String name;
	double price;
	Status status = Status.DISABLE;

	public Product(String name, double price) throws Exception {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
		this.isValid();
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public Boolean isValid() throws Exception {

		if (this.name.isBlank()) {
			throw new Exception("Name is required");
		}
		
		if (this.price < 0) {
			throw new Exception("the price cannot be less than 0");
		}


		return true;
	}

	public void enable() throws Exception {
		if (this.price <= 0) {
			throw new Exception("Price must be greater than 0");
		} else {
			this.status = Status.ENABLE;
		}

	}

	public void disable() throws Exception {
		if (this.price > 0) {
			throw new Exception("Price greater than 0");
		} else {
			this.status = Status.DISABLE;
		}
	}

	@Override
	public UUID getId() {

		return this.id;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public Status getStatus() {

		return this.status;
	}

	@Override
	public double getPrice() {

		return this.price;
	}

}
