package com.warehouse.dto.response;

import com.warehouse.enums.WarehouseStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    private Long id;

    private String warehouseCode;

    private String name;

    private String location;

    private Integer capacity;

    private WarehouseStatus status;
}