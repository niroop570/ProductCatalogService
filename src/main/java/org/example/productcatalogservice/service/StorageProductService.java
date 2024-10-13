package org.example.productcatalogservice.service;

import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) return optionalProduct.get();
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            Product oldProduct = optionalProduct.get();
            oldProduct.setState(product.getState());
            oldProduct.setImageUrl(product.getImageUrl());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setAmount(product.getAmount());
            oldProduct.setTitle(product.getTitle());
            oldProduct.setCategory(product.getCategory());
            oldProduct.setDescription(product.getDescription());
            return productRepository.save(oldProduct);
        }
        return null;
    }
}
