package com.warehouse.mapper;

import com.warehouse.dto.response.AuditLogResponse;
import com.warehouse.entity.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogResponse toResponse(AuditLog auditLog) {

        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .action(auditLog.getOperation())
                .entity(auditLog.getEntityName())
                .entityId(auditLog.getEntityId())
                .performedBy(auditLog.getPerformedBy())
                .referenceNo(auditLog.getPayload())
                .description(auditLog.getRemarks())
                .createdAt(auditLog.getPerformedAt())
                .build();
    }
}