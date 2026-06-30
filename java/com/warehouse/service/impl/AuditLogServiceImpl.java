package com.warehouse.service.impl;

import com.warehouse.dto.response.AuditLogResponse;
import com.warehouse.entity.AuditLog;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.mapper.AuditLogMapper;
import com.warehouse.repository.AuditLogRepository;
import com.warehouse.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    public void log(
            String action,
            String entity,
            Long entityId,
            String performedBy,
            String referenceNo,
            String description) {

        AuditLog auditLog = AuditLog.builder()
                .operation(action)
                .entityName(entity)
                .entityId(entityId)
                .performedBy(performedBy)
                .payload(referenceNo)
                .remarks(description)
                .build();

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public AuditLogResponse getAuditLogById(Long id) {

        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Audit Log not found with id : " + id));

        return auditLogMapper.toResponse(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getAllAuditLogs(
            int page,
            int size,
            String sortBy) {

    	Pageable pageable = PageRequest.of(
    	        page,
    	        size,
    	        Sort.by("performedAt").descending());

        return auditLogRepository.findAll(pageable)
                .map(auditLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getAuditLogsByEntity(
            String entity,
            int page,
            int size,
            String sortBy) {

    	Pageable pageable = PageRequest.of(
    	        page,
    	        size,
    	        Sort.by("performedAt").descending());

        return auditLogRepository
                .findByEntityName(entity, pageable)
                .map(auditLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getAuditLogsByAction(
            String action,
            int page,
            int size,
            String sortBy) {

    	Pageable pageable = PageRequest.of(
    	        page,
    	        size,
    	        Sort.by("performedAt").descending());

        return auditLogRepository
                .findByOperation(action, pageable)
                .map(auditLogMapper::toResponse);
    }
}