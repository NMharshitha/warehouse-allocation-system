package com.warehouse.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {

    private Long warehouseId;

    private Long productId;

    @PositiveOrZero
    private Integer totalQuantity;

    @PositiveOrZero
    private Integer reservedQuantity;

    @PositiveOrZero
    private Integer availableQuantity;
}