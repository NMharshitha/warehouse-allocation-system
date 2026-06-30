package com.warehouse.controller;

import com.warehouse.dto.response.AuditLogResponse;
import com.warehouse.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(
        name = "Audit Log API",
        description = "Audit Log Management"
)
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Operation(summary = "Get Audit Log By Id")
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponse> getAuditLogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogById(id));
    }

    @Operation(summary = "Get All Audit Logs")
    @GetMapping
    public ResponseEntity<Page<AuditLogResponse>> getAllAuditLogs(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "createdAt") String sortBy) {

        return ResponseEntity.ok(
                auditLogService.getAllAuditLogs(
                        page,
                        size,
                        sortBy));
    }

    @Operation(summary = "Search Audit Logs By Entity")
    @GetMapping("/entity/{entity}")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByEntity(

            @PathVariable String entity,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "createdAt") String sortBy) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByEntity(
                        entity,
                        page,
                        size,
                        sortBy));
    }

    @Operation(summary = "Search Audit Logs By Action")
    @GetMapping("/action/{action}")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByAction(

            @PathVariable String action,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "createdAt") String sortBy) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByAction(
                        action,
                        page,
                        size,
                        sortBy));
    }
}