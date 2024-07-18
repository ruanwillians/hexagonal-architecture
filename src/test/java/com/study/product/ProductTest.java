package com.study.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.study.application.Product;
import com.study.variables.Status;

class ProductTest {

	@Test
	void productValid() throws Exception {
		Product product = new Product( "test", 10.0);
		assertEquals("test", product.getName());
		assertEquals(10.0, product.getPrice());

	}

	@Test
	void productInvalidName() throws Exception {

		try {
			Product product = new Product("", 0);
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertEquals("Name is required", e.getMessage());
		}

	}

	@Test
	void productInvalidPrice() throws Exception {

		try {
			Product product = new Product("test", -10);
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertEquals("the price cannot be less than 0", e.getMessage());
		}

	}

	@Test
	void productTestEnable() throws Exception {
		Product product = new Product("test", 10.0);

		product.enable();

		assertEquals(Status.ENABLE, product.getStatus());

	}

	@Test
	void productTestEnableLessThanZero() throws Exception {
		try {
			Product product = new Product("test", 0);
			product.enable();
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertEquals("Price must be greater than 0", e.getMessage());
		}
	}

	@Test
	void productTestDisable() throws Exception {
		Product product = new Product("test", 0);

		product.disable();
		assertEquals(Status.DISABLE, product.getStatus());

	}

	@Test
	void productTestDisableGreaterThanZero() throws Exception {

		try {
			Product product = new Product("test", 2.0);
			product.disable();

		} catch (Exception e) {
			assertEquals("Price greater than 0", e.getMessage());
		}

	}

}
