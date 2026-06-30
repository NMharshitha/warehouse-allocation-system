package com.warehouse.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long warehouseId;

    @Positive
    private Integer quantity;

    private String remarks;
}