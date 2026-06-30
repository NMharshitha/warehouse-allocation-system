package com.warehouse.repository;

import com.warehouse.entity.Product;
import com.warehouse.entity.Warehouse;
import com.warehouse.entity.WarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryRepository
        extends JpaRepository<WarehouseInventory, Long> {

    Optional<WarehouseInventory> findByWarehouseAndProduct(
            Warehouse warehouse,
            Product product);

    List<WarehouseInventory> findByWarehouse(Warehouse warehouse);

    List<WarehouseInventory> findByProduct(Product product);

    List<WarehouseInventory> findByProductOrderByAvailableQuantityDesc(
            Product product);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT wi
        FROM WarehouseInventory wi
        WHERE wi.warehouse.id = :warehouseId
          AND wi.product.id = :productId
    """)
    Optional<WarehouseInventory> findByWarehouseAndProductForUpdate(
            @Param("warehouseId") Long warehouseId,
            @Param("productId") Long productId);
}