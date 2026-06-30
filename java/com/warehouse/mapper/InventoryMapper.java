package com.warehouse.mapper;

import com.warehouse.dto.response.InventoryResponse;
import com.warehouse.entity.WarehouseInventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryResponse toResponse(WarehouseInventory inventory) {

        return InventoryResponse.builder()
                .id(inventory.getId())

                .warehouseId(inventory.getWarehouse().getId())
                .warehouseCode(inventory.getWarehouse().getWarehouseCode())
                .warehouseName(inventory.getWarehouse().getName())

                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .sku(inventory.getProduct().getSku())

                .totalQuantity(inventory.getTotalQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .availableQuantity(inventory.getAvailableQuantity())

                .version(inventory.getVersion())
                .build();
    }
}