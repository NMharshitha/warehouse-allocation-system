package com.warehouse.service;

import com.warehouse.dto.request.AllocationRequest;
import com.warehouse.dto.response.AllocationResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AllocationService {

	AllocationResponse allocateStock(AllocationRequest request);

	AllocationResponse getAllocationById(Long id);

	Page<AllocationResponse> getAllAllocations(
	        int page,
	        int size,
	        String sortBy);

	Page<AllocationResponse> searchAllocations(
	        Long productId,
	        Long warehouseId,
	        LocalDate fromDate,
	        LocalDate toDate,
	        int page,
	        int size,
	        String sortBy);
}