package com.warehouse.service.impl;

import com.warehouse.dto.request.AllocationRequest;
import com.warehouse.dto.response.AllocationResponse;
import com.warehouse.entity.Allocation;
import com.warehouse.entity.Product;
import com.warehouse.entity.WarehouseInventory;
import com.warehouse.enums.AllocationStatus;
import com.warehouse.enums.WarehouseStatus;
import com.warehouse.exception.InsufficientStockException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.mapper.AllocationMapper;
import com.warehouse.repository.AllocationRepository;
import com.warehouse.repository.ProductRepository;
import com.warehouse.repository.WarehouseInventoryRepository;
import com.warehouse.service.AllocationService;
import com.warehouse.service.AuditLogService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class AllocationServiceImpl implements AllocationService {

    private final ProductRepository productRepository;
    private final WarehouseInventoryRepository inventoryRepository;
    private final AllocationRepository allocationRepository;
    private final AllocationMapper allocationMapper;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public AllocationResponse allocateStock(AllocationRequest request) {

        Product product = getProduct(request.getProductId());

        WarehouseInventory inventory = findLockedInventory(
                request.getWarehouseId(),
                request.getProductId());

        validateStock(inventory, request.getQuantity());

        validateWarehouse(inventory);

        updateInventory(inventory, request.getQuantity());

        Allocation allocation = createAllocation(
                product,
                inventory,
                request.getQuantity());

        safeAuditLog(allocation);

        return allocationMapper.toResponse(allocation);
    }
    
    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productId));
    }

    private WarehouseInventory findLockedInventory(Long warehouseId, Long productId) {

        return inventoryRepository
                .findByWarehouseAndProductForUpdate(warehouseId, productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found for warehouse=" + warehouseId +
                                        " product=" + productId));
    }

    private void validateStock(WarehouseInventory inventory, Integer qty) {
        if (inventory.getAvailableQuantity() < qty) {
            throw new InsufficientStockException("Insufficient stock available.");
        }
    }

    private void validateWarehouse(WarehouseInventory inventory) {

        if (inventory.getWarehouse() == null) {
            throw new ResourceNotFoundException("Warehouse not found");
        }

        if (inventory.getWarehouse().getStatus() != WarehouseStatus.ACTIVE) {
            throw new IllegalStateException("Warehouse is inactive");
        }

        if (Boolean.TRUE.equals(inventory.getWarehouse().getDeleted())) {
            throw new IllegalStateException("Warehouse is deleted");
        }
    }

    private void updateInventory(WarehouseInventory inventory, Integer qty) {

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - qty);

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + qty);

        inventoryRepository.save(inventory);   
    }

    private Allocation createAllocation(Product product,
                                        WarehouseInventory inventory,
                                        Integer qty) {

        Allocation allocation = Allocation.builder()
                .allocationReference("ALLOC-" + System.currentTimeMillis())
                .product(product)
                .warehouse(inventory.getWarehouse())
                .quantity(qty)
                .status(AllocationStatus.ALLOCATED)
                .remarks("Auto allocation")
                .build();

        return allocationRepository.save(allocation);
    }

   
    private void safeAuditLog(Allocation allocation) {
        try {
            auditLogService.log(
                    "ALLOCATE",
                    "Allocation",
                    allocation.getId(),
                    "SYSTEM",
                    allocation.getAllocationReference(),
                    "SUCCESS"
            );
        } catch (Exception ex) {
            // NEVER break main flow
            System.err.println("Audit log failed: " + ex.getMessage());
        }
    }
    @Override
    @Transactional(readOnly = true)
    public AllocationResponse getAllocationById(Long id) {

        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Allocation not found: " + id));

        return allocationMapper.toResponse(allocation);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AllocationResponse> getAllAllocations(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return allocationRepository.findAll(pageable)
                .map(allocationMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AllocationResponse> searchAllocations(
            Long productId,
            Long warehouseId,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Specification<Allocation> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (productId != null) {
                predicates.add(cb.equal(root.get("product").get("id"), productId));
            }

            if (warehouseId != null) {
                predicates.add(cb.equal(root.get("warehouse").get("id"), warehouseId));
            }

            if (fromDate != null && toDate != null) {
                predicates.add(cb.between(root.get("createdDate"), fromDate, toDate));
            } else if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), fromDate));
            } else if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), toDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return allocationRepository.findAll(spec, pageable)
                .map(allocationMapper::toResponse);
    }
}