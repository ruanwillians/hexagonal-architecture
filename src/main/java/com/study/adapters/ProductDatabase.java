package com.study.adapters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.application.Product;
import com.study.application.ProductInterface;
import com.study.application.ProductPersistenceInterface;
import com.study.variables.Status;

@Repository
public class ProductDatabase implements ProductPersistenceInterface {

    private final DataSource dataSource;

    @Autowired
    public ProductDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ProductInterface save(ProductInterface product) throws Exception {
        String sql = "INSERT INTO products (id, name, price, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getId().toString());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getStatus().toString());
            stmt.executeUpdate();
        }
        return product;
    }

    @Override
    public ProductInterface getId(UUID id) throws Exception {
        String sql = "SELECT id, name, price, status FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String status = rs.getString("status");
                    return createProduct(UUID.fromString(rs.getString("id")), name, price, Status.valueOf(status));
                }
            }
        }
        return null;
    }

    @Override
    public List<ProductInterface> getAll() throws Exception {
        List<ProductInterface> products = new ArrayList<>();
        String sql = "SELECT id, name, price, status FROM products";
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String status = rs.getString("status");
                products.add(createProduct(id, name, price, Status.valueOf(status)));
            }
        }
        return products;
    }

    private ProductInterface createProduct(UUID id, String name, double price, Status status) throws Exception {
        Product product = new Product(name, price);
        product.setId(id);
        product.setStatus(status);
        return product;
    }
}
