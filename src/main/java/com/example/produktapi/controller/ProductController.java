package com.example.produktapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.produktapi.service.ProductService;
import com.example.produktapi.model.Product;

import java.util.List;

// Lägg till CrossOrigin() för att se produkterna via en hemsida
@CrossOrigin()
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    ResponseEntity<List<Product>>getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/products/categories")
    ResponseEntity<List<String>> getAllCategories() {
        List<String> allProducts = productService.getAllCategories();
        return new ResponseEntity<List<String>>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/products/categories/{category}")
    ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List productsByCategory =  productService.getProductsByCategory(category);
        return new ResponseEntity<List<Product>>(productsByCategory, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        Product createdProduct = productService.addProduct(newProduct);
        return new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    ResponseEntity<Product> updateProduct(@RequestBody Product newProduct, @PathVariable Integer id) {
        Product updatedProduct = productService.updateProduct(newProduct, id);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<String>(String.format("Produkt med id %d har raderats", id), HttpStatus.OK);
    }
}
