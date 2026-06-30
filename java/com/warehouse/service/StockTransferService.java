package com.warehouse.service;

import com.warehouse.dto.request.StockTransferRequest;
import com.warehouse.dto.response.StockTransferResponse;
import org.springframework.data.domain.Page;

public interface StockTransferService {

    StockTransferResponse transferStock(StockTransferRequest request);

    StockTransferResponse getTransferById(Long id);

    Page<StockTransferResponse> getAllTransfers(
            int page,
            int size,
            String sortBy
    );

    Page<StockTransferResponse> getTransfersByProduct(
            Long productId,
            int page,
            int size,
            String sortBy
    );

    Page<StockTransferResponse> getTransfersByWarehouse(
            Long warehouseId,
            int page,
            int size,
            String sortBy
    );
}