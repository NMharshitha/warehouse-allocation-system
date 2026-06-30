package com.warehouse.dto.response;

import com.warehouse.enums.TransferStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferResponse {

    private Long id;

    private String referenceNo;

    private Long sourceWarehouseId;

    private String sourceWarehouseName;

    private Long targetWarehouseId;

    private String targetWarehouseName;

    private Long productId;

    private String productName;

    private Integer quantity;

    private TransferStatus status;

    private LocalDateTime transferDate;

}