package com.warehouse.mapper;

import org.springframework.stereotype.Component;

import com.warehouse.dto.request.ProductRequest;
import com.warehouse.dto.response.ProductResponse;
import com.warehouse.entity.Product;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {

        return Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .totalStock(request.getTotalStock())   // VERY IMPORTANT
                .build();
    }

    public ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .totalStock(product.getTotalStock())
                .build();
    }
}