package com.warehouse.entity;

import com.warehouse.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transfer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String referenceNo;

    @ManyToOne
    @JoinColumn(name = "source_warehouse_id")
    private Warehouse sourceWarehouse;

    @ManyToOne
    @JoinColumn(name = "target_warehouse_id")
    private Warehouse targetWarehouse;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
 
    
    

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private LocalDateTime transferDate;

    @PrePersist
    void prePersist() {
        transferDate = LocalDateTime.now();

        if (referenceNo == null) {
            referenceNo =
                    "TRF-" + System.currentTimeMillis();
        }
        
    }
    public void test() {
        System.out.println(getId());
        System.out.println(getReferenceNo());
        System.out.println(getStatus());
        System.out.println(getQuantity());
    }
}