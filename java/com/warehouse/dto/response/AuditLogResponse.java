package com.warehouse.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long id;

    private String action;

    private String entity;

    private Long entityId;

    private String performedBy;

    private String referenceNo;

    private String description;

    private LocalDateTime createdAt;

}