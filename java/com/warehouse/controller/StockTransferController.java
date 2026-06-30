package com.warehouse.controller;

import com.warehouse.dto.request.StockTransferRequest;
import com.warehouse.dto.response.StockTransferResponse;
import com.warehouse.service.StockTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-transfers")
@RequiredArgsConstructor
@Tag(
        name = "Stock Transfer API",
        description = "Warehouse Stock Transfer Management"
)
public class StockTransferController {

    private final StockTransferService stockTransferService;

    @Operation(summary = "Transfer Stock Between Warehouses")
    @PostMapping
    public ResponseEntity<StockTransferResponse> transferStock(
            @Valid @RequestBody StockTransferRequest request) {

        return new ResponseEntity<>(
                stockTransferService.transferStock(request),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get Stock Transfer By Id")
    @GetMapping("/{id}")
    public ResponseEntity<StockTransferResponse> getTransferById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                stockTransferService.getTransferById(id));
    }

    @Operation(summary = "Get All Stock Transfers")
    @GetMapping
    public ResponseEntity<Page<StockTransferResponse>> getAllTransfers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "transferDate") String sortBy) {

        return ResponseEntity.ok(
                stockTransferService.getAllTransfers(
                        page,
                        size,
                        sortBy));
    }

    @Operation(summary = "Search Transfers By Product")
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<StockTransferResponse>> getTransfersByProduct(

            @PathVariable Long productId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "transferDate") String sortBy) {

        return ResponseEntity.ok(
                stockTransferService.getTransfersByProduct(
                        productId,
                        page,
                        size,
                        sortBy));
    }

    @Operation(summary = "Search Transfers By Warehouse")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<Page<StockTransferResponse>> getTransfersByWarehouse(

            @PathVariable Long warehouseId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "transferDate") String sortBy) {

        return ResponseEntity.ok(
                stockTransferService.getTransfersByWarehouse(
                        warehouseId,
                        page,
                        size,
                        sortBy));
    }
}