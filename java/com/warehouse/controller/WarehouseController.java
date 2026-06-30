package com.warehouse.controller;

import com.warehouse.dto.request.WarehouseRequest;
import com.warehouse.dto.response.WarehouseResponse;
import com.warehouse.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Tag(name = "Warehouse API", description = "Warehouse Management Operations")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Operation(summary = "Create Warehouse")
    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(
            @Valid @RequestBody WarehouseRequest request) {

        return new ResponseEntity<>(
                warehouseService.createWarehouse(request),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get Warehouse By Id")
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                warehouseService.getWarehouseById(id));
    }

    @Operation(summary = "Get All Warehouses")
    @GetMapping
    public ResponseEntity<Page<WarehouseResponse>> getAllWarehouses(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(
                warehouseService.getAllWarehouses(page, size, sortBy));
    }

    @Operation(summary = "Update Warehouse")
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(

            @PathVariable Long id,

            @Valid @RequestBody WarehouseRequest request) {

        return ResponseEntity.ok(
                warehouseService.updateWarehouse(id, request));
    }

    @Operation(summary = "Activate Warehouse")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<String> activateWarehouse(
            @PathVariable Long id) {

        warehouseService.activateWarehouse(id);

        return ResponseEntity.ok("Warehouse activated successfully.");
    }

    @Operation(summary = "Deactivate Warehouse")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateWarehouse(
            @PathVariable Long id) {

        warehouseService.deactivateWarehouse(id);

        return ResponseEntity.ok("Warehouse deactivated successfully.");
    }

    @Operation(summary = "Soft Delete Warehouse")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(
            @PathVariable Long id) {

        warehouseService.deleteWarehouse(id);

        return ResponseEntity.ok("Warehouse deleted successfully.");
    }
}