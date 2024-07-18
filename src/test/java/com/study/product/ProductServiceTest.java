package com.study.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.study.application.Product;
import com.study.application.ProductInterface;
import com.study.application.ProductPersistenceInterface;
import com.study.application.ProductService;
import com.study.variables.Status;

class ProductServiceTest {
	
	@Test
	void testGetAllProductsService() throws Exception {
	    
	    ProductPersistenceInterface productPersistence = mock(ProductPersistenceInterface.class);

	 
	    Product product1 = mock(Product.class);
	    when(product1.getName()).thenReturn("teste1");
	    when(product1.getPrice()).thenReturn(10.0);

	    Product product2 = mock(Product.class);
	    when(product2.getName()).thenReturn("teste2");
	    when(product2.getPrice()).thenReturn(20.0);


	    List<ProductInterface> productList = Arrays.asList(product1, product2);

	
	    when(productPersistence.getAll()).thenReturn(productList);

	  
	    ProductService productService = new ProductService(productPersistence);

	  
	    List<ProductInterface> retrievedProducts = productService.getAll();

	    assertEquals(productList, retrievedProducts);
	    assertEquals(2, retrievedProducts.size());
	    assertEquals("teste1", retrievedProducts.get(0).getName());
	    assertEquals(10.0, retrievedProducts.get(0).getPrice());
	    assertEquals("teste2", retrievedProducts.get(1).getName());
	    assertEquals(20.0, retrievedProducts.get(1).getPrice());
	}

	@Test
	void testGetByIdMethodService() throws Exception {
		ProductPersistenceInterface productPersistence = mock(ProductPersistenceInterface.class);
		Product product = mock(Product.class);
		when(product.getName()).thenReturn("teste");
		when(product.getPrice()).thenReturn(10.0);

		UUID productId = product.getId();
		when(productPersistence.getId(productId)).thenReturn(product);

		ProductService productService = new ProductService(productPersistence);

		ProductInterface retrievedProduct = productService.getId(productId);

		assertEquals(product, retrievedProduct);

	}
	
	  @Test
	    void testCreateMethodService() throws Exception {
	        ProductPersistenceInterface productPersistence = mock(ProductPersistenceInterface.class);
	        Product product = mock(Product.class);
	        when(product.getName()).thenReturn("teste");
	        when(product.getPrice()).thenReturn(10.0);
	        when(productPersistence.save(any(ProductInterface.class))).thenReturn(product);

	        ProductService productService = new ProductService(productPersistence);

	        ProductInterface retrievedProduct = productService.create(product.getName(), product.getPrice());

	        assertEquals(product, retrievedProduct);
	    }
	  
	   @Test
	    void testEnableMethodService() throws Exception {

	        ProductPersistenceInterface productPersistence = mock(ProductPersistenceInterface.class);
	        ProductInterface product = mock(Product.class);


	        when(product.getStatus()).thenReturn(Status.ENABLE);
	        when(productPersistence.save(any(ProductInterface.class))).thenReturn(product);


	        ProductService productService = new ProductService(productPersistence);

	 
	        ProductInterface retrievedProduct = productService.enable(product);

	        assertEquals(Status.ENABLE, retrievedProduct.getStatus());
	    }
	   
	   @Test
	    void testDisableMethodService() throws Exception {

	        ProductPersistenceInterface productPersistence = mock(ProductPersistenceInterface.class);
	        ProductInterface product = mock(Product.class);


	        when(product.getStatus()).thenReturn(Status.DISABLE);
	        when(productPersistence.save(any(ProductInterface.class))).thenReturn(product);


	        ProductService productService = new ProductService(productPersistence);

	 
	        ProductInterface retrievedProduct = productService.disable(product);

	        assertEquals(Status.DISABLE, retrievedProduct.getStatus());
	    }

}
