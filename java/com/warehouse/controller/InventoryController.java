package com.warehouse.controller;

import com.warehouse.dto.request.InventoryRequest;
import com.warehouse.dto.response.InventoryResponse;
import com.warehouse.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(
        name = "Inventory API",
        description = "Warehouse Inventory Management"
)
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Create Inventory")
    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(
            @Valid @RequestBody InventoryRequest request) {

        return new ResponseEntity<>(
                inventoryService.save(request),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Inventory")
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {

        return ResponseEntity.ok(
                inventoryService.getAll());
    }

    @Operation(summary = "Get Inventory By Id")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                inventoryService.getById(id));
    }

    @Operation(summary = "Update Inventory")
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest request) {

        return ResponseEntity.ok(
                inventoryService.update(id, request));
    }

    @Operation(summary = "Delete Inventory")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(
            @PathVariable Long id) {

        inventoryService.delete(id);

        return ResponseEntity.ok("Inventory deleted successfully.");
    }

    @Operation(summary = "Get Inventory By Warehouse")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryResponse>> getInventoryByWarehouse(
            @PathVariable Long warehouseId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByWarehouse(warehouseId));
    }

    @Operation(summary = "Get Inventory By Product")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryResponse>> getInventoryByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByProduct(productId));
    }
}