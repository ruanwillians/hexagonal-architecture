package com.study.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductserviceInterface {

	@Autowired
    private final ProductPersistenceInterface persistence;

    @Autowired
    public ProductService(ProductPersistenceInterface persistence) {
        this.persistence = persistence;
    }

    @Override
    public List<ProductInterface> getAll() throws Exception {
        try {
            return persistence.getAll();
        } catch (Exception e) {
            throw new Exception("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }
    }

    @Override
    public ProductInterface getId(UUID id) throws Exception {
        try {
            return persistence.getId(id);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar produto por ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public ProductInterface create(String name, double price) throws Exception {
        try {
            ProductInterface product = new Product(name, price);
            return persistence.save(product);
        } catch (Exception e) {
            throw new Exception("Erro ao criar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public ProductInterface enable(ProductInterface product) throws Exception {
        try {
            product.enable();
            return persistence.save(product);
        } catch (Exception e) {
            throw new Exception("Erro ao habilitar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public ProductInterface disable(ProductInterface product) throws Exception {
        try {
            product.disable();
            return persistence.save(product);
        } catch (Exception e) {
            throw new Exception("Erro ao desabilitar produto: " + e.getMessage(), e);
        }
    }
}
