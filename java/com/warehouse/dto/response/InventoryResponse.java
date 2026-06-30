package com.warehouse.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {

    private Long id;

    private Long warehouseId;

    private String warehouseCode;

    private String warehouseName;

    private Long productId;

    private String productName;

    private String sku;

    private Integer totalQuantity;

    private Integer reservedQuantity;

    private Integer availableQuantity;

    private Long version;
}