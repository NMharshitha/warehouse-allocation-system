package com.warehouse.service;

import com.warehouse.dto.request.WarehouseRequest;
import com.warehouse.dto.response.WarehouseResponse;
import org.springframework.data.domain.Page;

public interface WarehouseService {

    WarehouseResponse createWarehouse(WarehouseRequest request);

    WarehouseResponse updateWarehouse(Long id, WarehouseRequest request);

    WarehouseResponse getWarehouseById(Long id);

    Page<WarehouseResponse> getAllWarehouses(int page, int size, String sortBy);

    void activateWarehouse(Long id);

    void deactivateWarehouse(Long id);

    void deleteWarehouse(Long id);
}