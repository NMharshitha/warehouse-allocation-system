package com.warehouse.service;

import com.warehouse.dto.response.AuditLogResponse;
import org.springframework.data.domain.Page;

public interface AuditLogService {

    void log(
            String action,
            String entity,
            Long entityId,
            String performedBy,
            String referenceNo,
            String description);

    AuditLogResponse getAuditLogById(Long id);

    Page<AuditLogResponse> getAllAuditLogs(
            int page,
            int size,
            String sortBy);

    Page<AuditLogResponse> getAuditLogsByEntity(
            String entity,
            int page,
            int size,
            String sortBy);

    Page<AuditLogResponse> getAuditLogsByAction(
            String action,
            int page,
            int size,
            String sortBy);
}