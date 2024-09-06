package com.tutorial.apidemo.Springboot.tutorial.controllers;

import com.tutorial.apidemo.Springboot.tutorial.models.Product;
import com.tutorial.apidemo.Springboot.tutorial.models.ResponseObject;
import com.tutorial.apidemo.Springboot.tutorial.repositories.ProductRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Products") // Anh xa request
public class ProductController {
    // DI : Dependency Injection
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/getAllProducts")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // You must save this to Database, now we have H2 DB = In memory database
    // You can also send request using Postman

    // Get detail product
    @GetMapping("/{id}")
    // Optional: co the null
    // Let's return an object with: data, message, status
    ResponseEntity<ResponseObject> findById(@PathVariable long id) {
       Optional<Product> foundProduct = productRepository.findById(id);
       // ternary type
       return foundProduct.isPresent() ?
         ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query object successfully", foundProduct)
        ) :
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("error", "Product not found with id: " + id, "")
        );
    }

    // Insert product
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        // 2 products must not have the same name !
        List<Product> foundProduct = productRepository.findByProductName(newProduct.getProductName().trim());
        if (foundProduct.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("error", "Product name already taken", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert product successfully",productRepository.save(newProduct)));
    }

    // Update, insert => update if found, otherwise insert --> upsert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable long id, @RequestBody Product newProduct) {
        Product updatedProduct =  productRepository.findById(id).map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setPrice(newProduct.getPrice());
            product.setYear(newProduct.getYear());
            product.setUrl(newProduct.getUrl());
            return productRepository.save(product);
        }).orElseGet(() -> {
             newProduct.setId(id);
             return productRepository.save(newProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update product successfully", updatedProduct));
    }

    // Delete product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {
        boolean exists = productRepository.existsById(id);
        if(exists) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete product successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("error", "Product not found with id: " + id, "")
        );
    }
}
