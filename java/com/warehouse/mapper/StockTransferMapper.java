package com.warehouse.mapper;

import com.warehouse.dto.response.StockTransferResponse;
import com.warehouse.entity.StockTransfer;
import org.springframework.stereotype.Component;

@Component
public class StockTransferMapper {

    public StockTransferResponse toResponse(StockTransfer transfer) {

        return StockTransferResponse.builder()
                .id(transfer.getId())
                .referenceNo(transfer.getReferenceNo())

                .sourceWarehouseId(
                        transfer.getSourceWarehouse().getId())
                .sourceWarehouseName(
                        transfer.getSourceWarehouse().getName())

                .targetWarehouseId(
                        transfer.getTargetWarehouse().getId())
                .targetWarehouseName(
                        transfer.getTargetWarehouse().getName())

                .productId(
                        transfer.getProduct().getId())
                .productName(
                        transfer.getProduct().getName())

                .quantity(
                        transfer.getQuantity())

                .status(
                        transfer.getStatus())

                .transferDate(
                        transfer.getTransferDate())

                .build();
    }

}