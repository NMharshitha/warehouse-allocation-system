package com.warehouse.repository;

import com.warehouse.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Page<AuditLog> findByEntityName(
            String entityName,
            Pageable pageable);

    Page<AuditLog> findByOperation(
            String operation,
            Pageable pageable);
}