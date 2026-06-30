package com.warehouse.mapper;

import com.warehouse.dto.request.WarehouseRequest;
import com.warehouse.dto.response.WarehouseResponse;
import com.warehouse.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    Warehouse toEntity(WarehouseRequest request);

    WarehouseResponse toResponse(Warehouse warehouse);

} 