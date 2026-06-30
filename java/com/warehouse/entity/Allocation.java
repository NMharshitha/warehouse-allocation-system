package com.warehouse.entity;

import com.warehouse.enums.AllocationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "allocation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String allocationReference;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private AllocationStatus status;

    private String remarks;

    private LocalDateTime allocatedAt;

    @PrePersist
    void prePersist() {
        allocatedAt = LocalDateTime.now();

        if (allocationReference == null) {
            allocationReference =
                    "ALLOC-" + System.currentTimeMillis();
        }
    }
}