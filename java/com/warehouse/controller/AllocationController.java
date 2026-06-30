package com.warehouse.controller;

import com.warehouse.dto.request.AllocationRequest;
import com.warehouse.dto.response.AllocationResponse;
import com.warehouse.service.AllocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/allocations")
@RequiredArgsConstructor
@Tag(
        name = "Allocation API",
        description = "Warehouse Stock Allocation Management"
)
public class AllocationController {

    private final AllocationService allocationService;

    @Operation(summary = "Allocate Product Stock")
    @PostMapping
    public ResponseEntity<AllocationResponse> allocateStock(
            @Valid @RequestBody AllocationRequest request) {

        return new ResponseEntity<>(
                allocationService.allocateStock(request),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get Allocation By Id")
    @GetMapping("/{id}")
    public ResponseEntity<AllocationResponse> getAllocationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                allocationService.getAllocationById(id));
    }

    @Operation(summary = "Get All Allocations")
    @GetMapping
    public ResponseEntity<Page<AllocationResponse>> getAllAllocations(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "allocatedAt") String sortBy) {

        return ResponseEntity.ok(
                allocationService.getAllAllocations(
                        page,
                        size,
                        sortBy));
    }

    @Operation(summary = "Search Allocations")
    @GetMapping("/search")
    public ResponseEntity<Page<AllocationResponse>> searchAllocations(

            @RequestParam(required = false) Long productId,

            @RequestParam(required = false) Long warehouseId,

            @RequestParam(required = false) LocalDate fromDate,

            @RequestParam(required = false) LocalDate toDate,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "allocatedAt") String sortBy) {

        return ResponseEntity.ok(

                allocationService.searchAllocations(

                        productId,
                        warehouseId,
                        fromDate,
                        toDate,
                        page,
                        size,
                        sortBy));
    }
}