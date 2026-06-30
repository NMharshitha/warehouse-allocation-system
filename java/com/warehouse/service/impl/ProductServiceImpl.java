package com.warehouse.service.impl;

import com.warehouse.dto.request.ProductRequest;
import com.warehouse.dto.response.ProductResponse;
import com.warehouse.entity.Product;
import com.warehouse.exception.ResourceAlreadyExistsException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.mapper.ProductMapper;
import com.warehouse.repository.ProductRepository;
import com.warehouse.service.AuditLogService;
import com.warehouse.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuditLogService auditLogService;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new ResourceAlreadyExistsException(
                    "Product already exists with SKU : " + request.getSku());
        }

        Product product = productMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        auditLogService.log(
                "CREATE",
                "Product",
                savedProduct.getId(),
                "SYSTEM",
                savedProduct.getSku(),
                "Product Created Successfully"
        );

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id,
                                         ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id : " + id));

        if (!product.getSku().equals(request.getSku())
                && productRepository.existsBySku(request.getSku())) {

            throw new ResourceAlreadyExistsException(
                    "SKU already exists.");
        }

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setDescription(request.getDescription());
        product.setTotalStock(request.getTotalStock());

        Product updatedProduct = productRepository.save(product);

        auditLogService.log(
                "UPDATE",
                "Product",
                updatedProduct.getId(),
                "SYSTEM",
                updatedProduct.getSku(),
                "Product Updated Successfully"
        );

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id : " + id));

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(int page,
                                                int size,
                                                String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending());

        return productRepository
                .findAll(pageable)
                .map(productMapper::toResponse);
    }
}