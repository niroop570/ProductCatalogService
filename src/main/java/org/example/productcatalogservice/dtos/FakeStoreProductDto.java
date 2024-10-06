package org.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private String category;
    private String title;
}
