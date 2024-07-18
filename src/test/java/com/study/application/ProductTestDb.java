package com.study.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.study.adapters.ProductDatabase;
import com.study.datasource.DataSourceConfig;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(DataSourceConfig.class)
public class ProductTestDb {

    @Autowired
    private DataSource dataSource;

    private ProductPersistenceInterface persistence;

    @BeforeEach
    public void setUp() throws Exception {
        persistence = new ProductDatabase(dataSource);

        // Your existing setup code remains unchanged
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE products (" +
                    "id VARCHAR(255) NOT NULL," +
                    "name VARCHAR(255) NOT NULL," +
                    "price FLOAT NOT NULL," +
                    "status VARCHAR(255) NOT NULL)";
            stmt.execute(createTableSQL);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            String dropTableSQL = "DROP TABLE products";
            stmt.execute(dropTableSQL);
        }
    }

    @Test
    public void testGetId() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            String insertSQL = "INSERT INTO products (id, name, price, status) VALUES ('10fa756d-4c93-4c5e-ae96-86ca5d0b26b2', 'Product A', 10.99, 'DISABLE')";
            stmt.execute(insertSQL);
        }

        ProductService productService = new ProductService(persistence);
        ProductInterface product = productService.getId(UUID.fromString("10fa756d-4c93-4c5e-ae96-86ca5d0b26b2"));
        assertNotNull(product);
        assertEquals(UUID.fromString("10fa756d-4c93-4c5e-ae96-86ca5d0b26b2"), product.getId());
        assertEquals("Product A", product.getName());
        assertEquals(10.99, product.getPrice());
    }

    @Test
    public void testGetAll() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            String insertSQL1 = "INSERT INTO products (id, name, price, status) VALUES ('10fa756d-4c93-4c5e-ae96-86ca5d0b26b2', 'Product A', 10.99, 'ENABLE')";
            String insertSQL2 = "INSERT INTO products (id, name, price, status) VALUES ('20fa756d-4c93-4c5e-ae96-86ca5d0b26b3', 'Product B', 20.99, 'DISABLE')";
            stmt.execute(insertSQL1);
            stmt.execute(insertSQL2);
        }

        ProductService productService = new ProductService(persistence);
        List<ProductInterface> products = productService.getAll();

        assertNotNull(products);
        assertEquals(2, products.size());

        ProductInterface product1 = products.get(0);
        assertEquals(UUID.fromString("10fa756d-4c93-4c5e-ae96-86ca5d0b26b2"), product1.getId());
        assertEquals("Product A", product1.getName());
        assertEquals(10.99, product1.getPrice());

        ProductInterface product2 = products.get(1);
        assertEquals(UUID.fromString("20fa756d-4c93-4c5e-ae96-86ca5d0b26b3"), product2.getId());
        assertEquals("Product B", product2.getName());
        assertEquals(20.99, product2.getPrice());
    }

    @Test
    public void testCreate() throws Exception {
        ProductService productService = new ProductService(persistence);

        ProductInterface createdProduct = productService.create("New Product", 15.99);

        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        assertEquals("New Product", createdProduct.getName());
        assertEquals(15.99, createdProduct.getPrice());

        ProductInterface retrievedProduct = persistence.getId(createdProduct.getId());
        assertNotNull(retrievedProduct);
        assertEquals(createdProduct.getId(), retrievedProduct.getId());
        assertEquals(createdProduct.getName(), retrievedProduct.getName());
        assertEquals(createdProduct.getPrice(), retrievedProduct.getPrice());
    }
}
