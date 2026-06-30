package com.warehouse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(
        name = "warehouse_inventory",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"warehouse_id","product_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer totalQuantity;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer reservedQuantity = 0;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer availableQuantity;

    @Version
    private Long version;
}