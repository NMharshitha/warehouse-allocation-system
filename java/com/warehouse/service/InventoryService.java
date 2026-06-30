package com.warehouse.service;

import com.warehouse.dto.request.InventoryRequest;
import com.warehouse.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse save(InventoryRequest request);

    InventoryResponse getById(Long id);

    List<InventoryResponse> getAll();

    InventoryResponse update(Long id, InventoryRequest request);

    void delete(Long id);

    List<InventoryResponse> getInventoryByWarehouse(Long warehouseId);

    List<InventoryResponse> getInventoryByProduct(Long productId);
}