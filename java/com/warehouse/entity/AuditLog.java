package com.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;

    private String entityName;

    private Long entityId;

    private String performedBy;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String remarks;

    private LocalDateTime performedAt;

    @PrePersist
    void prePersist() {
        performedAt = LocalDateTime.now();
    }
}