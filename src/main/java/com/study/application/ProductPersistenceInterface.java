package com.study.application;

import java.util.List;
import java.util.UUID;


public interface ProductPersistenceInterface {
	
	ProductInterface save(ProductInterface product) throws Exception;
	ProductInterface getId(UUID id) throws Exception;
	List<ProductInterface> getAll() throws Exception;

}
