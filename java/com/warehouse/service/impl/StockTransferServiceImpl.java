package com.warehouse.service.impl;

import com.warehouse.dto.request.StockTransferRequest;
import com.warehouse.dto.response.StockTransferResponse;
import com.warehouse.entity.Product;
import com.warehouse.entity.StockTransfer;
import com.warehouse.entity.Warehouse;
import com.warehouse.entity.WarehouseInventory;
import com.warehouse.enums.TransferStatus;
import com.warehouse.exception.InsufficientStockException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.mapper.StockTransferMapper;
import com.warehouse.repository.ProductRepository;
import com.warehouse.repository.StockTransferRepository;
import com.warehouse.repository.WarehouseInventoryRepository;
import com.warehouse.repository.WarehouseRepository;
import com.warehouse.service.AuditLogService;
import com.warehouse.service.StockTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockTransferServiceImpl implements StockTransferService {

    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final WarehouseInventoryRepository inventoryRepository;
    private final StockTransferRepository stockTransferRepository;
    private final StockTransferMapper stockTransferMapper;
    private final AuditLogService auditLogService;

    @Override
    public StockTransferResponse transferStock(StockTransferRequest request) {

        Warehouse sourceWarehouse = warehouseRepository.findById(request.getSourceWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Source warehouse not found"));

        Warehouse targetWarehouse = warehouseRepository.findById(request.getTargetWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Target warehouse not found"));
        if (request.getSourceWarehouseId().equals(request.getTargetWarehouseId())) {
            throw new IllegalArgumentException(
                    "Source and Target warehouse cannot be the same.");
        } 
        
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        WarehouseInventory sourceInventory =
                inventoryRepository.findByWarehouseAndProduct(sourceWarehouse, product)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Source inventory not found"));

        WarehouseInventory targetInventory =
                inventoryRepository.findByWarehouseAndProduct(targetWarehouse, product)
                        .orElseGet(() -> {

                            WarehouseInventory inventory = WarehouseInventory.builder()
                                    .warehouse(targetWarehouse)
                                    .product(product)
                                    .totalQuantity(0)
                                    .reservedQuantity(0)
                                    .availableQuantity(0)
                                    .build();

                            return inventoryRepository.save(inventory);
                        });

        if (sourceInventory.getAvailableQuantity() < request.getQuantity()) {

            throw new InsufficientStockException(
                    "Insufficient stock in source warehouse");
        }

        try {

           
            sourceInventory.setAvailableQuantity(
                    sourceInventory.getAvailableQuantity() - request.getQuantity());

            sourceInventory.setTotalQuantity(
                    sourceInventory.getTotalQuantity() - request.getQuantity());
     targetInventory.setAvailableQuantity(
                    targetInventory.getAvailableQuantity() + request.getQuantity());

            targetInventory.setTotalQuantity(
                    targetInventory.getTotalQuantity() + request.getQuantity());

            inventoryRepository.save(sourceInventory);
            inventoryRepository.save(targetInventory);

        } catch (OptimisticLockingFailureException ex) {

            throw new RuntimeException(
                    "Inventory modified by another transaction. Please retry.");
        }

        StockTransfer transfer = StockTransfer.builder()
                .sourceWarehouse(sourceWarehouse)
                .targetWarehouse(targetWarehouse)
                .product(product)
                .quantity(request.getQuantity())
                .status(TransferStatus.COMPLETED)
                .build();

        transfer = stockTransferRepository.save(transfer);

        auditLogService.log(
                "TRANSFER",
                "StockTransfer",
                transfer.getId(),
                "SYSTEM",
                transfer.getReferenceNo(),
                "Stock transferred successfully");

        return stockTransferMapper.toResponse(transfer);
    }
    @Override
    @Transactional(readOnly = true)
    public StockTransferResponse getTransferById(Long id) {

        StockTransfer transfer = stockTransferRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transfer not found with id : " + id));

        return stockTransferMapper.toResponse(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockTransferResponse> getAllTransfers(int page,
                                                       int size,
                                                       String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending());

        return stockTransferRepository
                .findAll(pageable)
                .map(stockTransferMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<StockTransferResponse> getTransfersByProduct(
            Long productId,
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending());

        return stockTransferRepository
                .findByProductId(productId, pageable)
                .map(stockTransferMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockTransferResponse> getTransfersByWarehouse(
            Long warehouseId,
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending());

        return stockTransferRepository
                .findBySourceWarehouseIdOrTargetWarehouseId(
                        warehouseId,
                        warehouseId,
                        pageable)
                .map(stockTransferMapper::toResponse);
    }
}