package org.example.productcatalogservice.clients;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public List<FakeStoreProductDto> getAllProducts() {
//        ResponseEntity<List<FakeStoreProductDto>> listResponseEntity = restTemplate.exchange("https://fakestoreapi.com/products", HttpMethod.GET,null, new ParameterizedTypeReference<List<FakeStoreProductDto>>() {});
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products", HttpMethod.GET, null, FakeStoreProductDto[].class);
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreProductDtoResponseEntity.getBody();
        List<FakeStoreProductDto> fakeStoreProductDtoList = new ArrayList<>();
        if (fakeStoreProductDtos != null) {
            fakeStoreProductDtoList.addAll(Arrays.asList(fakeStoreProductDtos));
        }
        return fakeStoreProductDtoList;
    }

    public FakeStoreProductDto getProductById(long id) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.GET, null, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if (fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDto != null)
            return fakeStoreProductDto;
        return null;
    }

    private  <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
