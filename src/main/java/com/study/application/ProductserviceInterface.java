package com.study.application;

import java.util.List;
import java.util.UUID;


public interface ProductserviceInterface {
	List<ProductInterface> getAll() throws Exception;
	ProductInterface getId(UUID id) throws Exception;
	ProductInterface create(String name, double price) throws Exception;
	ProductInterface enable(ProductInterface product) throws Exception;
	ProductInterface disable(ProductInterface product) throws Exception;
}
