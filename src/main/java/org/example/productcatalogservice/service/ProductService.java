package org.example.productcatalogservice.service;

import org.example.productcatalogservice.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(long id);
    Product addProduct(Product product);
    Product replaceProduct(long id, Product product);
}
