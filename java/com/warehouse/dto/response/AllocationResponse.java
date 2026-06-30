package com.warehouse.dto.response;

import com.warehouse.enums.AllocationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationResponse {

    private Long id;

    private String allocationReference;

    private Long productId;

    private String productName;

    private Long warehouseId;

    private String warehouseName;

    private Integer quantity;

    private AllocationStatus status;

    private String remarks;

    private LocalDateTime allocatedAt;
}