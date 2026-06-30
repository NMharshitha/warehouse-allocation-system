package com.warehouse.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferRequest {

    @NotNull
    private Long sourceWarehouseId;

    @NotNull
    private Long targetWarehouseId;

    @NotNull
    private Long productId;

    @Positive
    private Integer quantity;
}