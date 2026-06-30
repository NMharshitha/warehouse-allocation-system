package com.warehouse.mapper;

import com.warehouse.dto.response.AllocationResponse;
import com.warehouse.entity.Allocation;
import org.springframework.stereotype.Component;

@Component
public class AllocationMapper {

    public AllocationResponse toResponse(Allocation allocation) {

        return AllocationResponse.builder()
                .id(allocation.getId())
                .allocationReference(allocation.getAllocationReference())
                .productId(allocation.getProduct().getId())
                .productName(allocation.getProduct().getName())
                .warehouseId(allocation.getWarehouse().getId())
                .warehouseName(allocation.getWarehouse().getName())
                .quantity(allocation.getQuantity())
                .status(allocation.getStatus())
                .remarks(allocation.getRemarks())
                .allocatedAt(allocation.getAllocatedAt())
                .build();
    }
}