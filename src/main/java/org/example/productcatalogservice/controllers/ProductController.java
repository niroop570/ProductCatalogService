package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        return null;
    }

    @GetMapping("/products/{id}")
//    public Product getProductById(Long id) {
    public Product getProductById(@PathVariable("id") Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setTitle("Pixal 6a");
        product.setDescription("This is Pixal 6a");
        product.setAmount(160000.0);
        return product;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return null;
    }
}
