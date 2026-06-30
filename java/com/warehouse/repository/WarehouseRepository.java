package com.warehouse.repository;

import com.warehouse.entity.Warehouse;
import com.warehouse.enums.WarehouseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByWarehouseCode(String warehouseCode);

    boolean existsByWarehouseCode(String warehouseCode);

    List<Warehouse> findByStatusAndDeletedFalse(WarehouseStatus status);

}