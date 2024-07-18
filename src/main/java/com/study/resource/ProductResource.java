package com.study.resource;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.Dto.ProductDTO;
import com.study.application.ProductInterface;
import com.study.application.ProductService;

@RestController
@RequestMapping("/products") 
public class ProductResource {

    @Autowired
    private ProductService productService;
   
    @GetMapping("")
    public ResponseEntity<List<ProductInterface>> getProducts() {
        try {
            List<ProductInterface> products = productService.getAll();
            if (!products.isEmpty()) {
                return new ResponseEntity<>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductInterface> getProductById(@PathVariable UUID id) {
        try {
            ProductInterface product = productService.getId(id);
            if (product != null) {
                return new ResponseEntity<ProductInterface>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("")
    public ResponseEntity<ProductInterface> createProduct(@RequestBody ProductDTO product) {
        try {
            ProductInterface createdProduct = productService.create(product.getName(), product.getPrice());
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); 
        } catch (Exception e) {
            System.out.println("Erro ao criar produto: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
}
