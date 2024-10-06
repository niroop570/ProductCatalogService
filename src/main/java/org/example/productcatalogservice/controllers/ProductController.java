package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductDto> getProducts() {
        List<Product> productList = productService.getAllProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            productDtoList.add(from(product));
        }
        return productDtoList;
    }

    @GetMapping("/products/{id}")
//    public Product getProductById(Long id) {
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
//        Product product = new Product();
//        product.setId(productId);
//        product.setTitle("Pixal 6a");
//        product.setDescription("This is Pixal 6a");
//        product.setAmount(160000.0);
        try {
            if(productId < 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id must be a positive integer");
            }
            Product product = productService.getProductById(productId);
            if(product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(from(product), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        try {
            if(productDto == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product can not be empty positive integer");
            }
//            if(productDto.getId() == null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id must be a positive integer");
//            }
            Product product = from(productDto);
            Product response = productService.addProduct(product);
            if(response == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(from(response), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Product product = productService.replaceProduct(id, from(productDto));
        return from(product);
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setAmount(productDto.getAmount());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            category.setId(productDto.getId());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setAmount(product.getAmount());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            categoryDto.setId(product.getId());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
