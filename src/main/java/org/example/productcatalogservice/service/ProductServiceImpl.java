package org.example.productcatalogservice.service;

import org.example.productcatalogservice.clients.FakeStoreApiClient;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtoList = fakeStoreApiClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        if (fakeStoreProductDtoList != null) {
            for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoList) {
                products.add(from(fakeStoreProductDto));
            }
        }
        return products;
    }

    public Product getProductById(long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);
        return from(fakeStoreProductDto);
    }

    public Product addProduct(Product product) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products", HttpMethod.POST, from(product), FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if (fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDto != null)
            return from(fakeStoreProductDto);
        return null;
    }

    public Product replaceProduct(long id, Product product) {
        FakeStoreProductDto fakeStoreProductDtoResponse = requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT,from(product), FakeStoreProductDto.class,id).getBody();
        if (fakeStoreProductDtoResponse != null) {
            return from(fakeStoreProductDtoResponse);
        }
        return null;
    }

    private  <T> ResponseEntity<T> requestForEntity(String url,HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setAmount(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getAmount());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        return fakeStoreProductDto;
    }
}
