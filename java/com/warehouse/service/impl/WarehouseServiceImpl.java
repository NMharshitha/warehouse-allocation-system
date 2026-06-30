package com.warehouse.service.impl;

import com.warehouse.dto.request.WarehouseRequest;
import com.warehouse.dto.response.WarehouseResponse;
import com.warehouse.entity.Warehouse;
import com.warehouse.enums.WarehouseStatus;
import com.warehouse.exception.ResourceAlreadyExistsException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.mapper.WarehouseMapper;
import com.warehouse.repository.WarehouseRepository;
import com.warehouse.service.AuditLogService;
import com.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final AuditLogService auditLogService;

    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest request) {

        if (warehouseRepository.existsByWarehouseCode(request.getWarehouseCode())) {
            throw new ResourceAlreadyExistsException(
                    "Warehouse already exists with code : " + request.getWarehouseCode());
        }

        Warehouse warehouse = warehouseMapper.toEntity(request);

        warehouse.setStatus(WarehouseStatus.ACTIVE);
        warehouse.setDeleted(false);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        auditLogService.log(
                "CREATE",
                "Warehouse",
                savedWarehouse.getId(),
                "SYSTEM",
                savedWarehouse.getWarehouseCode(),
                "Warehouse Created Successfully"
        );

        return warehouseMapper.toResponse(savedWarehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseResponse getWarehouseById(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .filter(w -> !Boolean.TRUE.equals(w.getDeleted()))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found with id : " + id));

        return warehouseMapper.toResponse(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseResponse> getAllWarehouses(int page,
                                                    int size,
                                                    String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        return warehouseRepository.findAll(pageable)
                .map(warehouseMapper::toResponse);
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id,
                                             WarehouseRequest request) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found with id : " + id));

        if (!warehouse.getWarehouseCode().equals(request.getWarehouseCode())
                && warehouseRepository.existsByWarehouseCode(request.getWarehouseCode())) {

            throw new ResourceAlreadyExistsException(
                    "Warehouse code already exists.");
        }

        warehouse.setWarehouseCode(request.getWarehouseCode());
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        warehouse.setCapacity(request.getCapacity());

        if (request.getStatus() != null) {
            warehouse.setStatus(request.getStatus());
        }

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        auditLogService.log(
                "UPDATE",
                "Warehouse",
                updatedWarehouse.getId(),
                "SYSTEM",
                updatedWarehouse.getWarehouseCode(),
                "Warehouse Updated Successfully"
        );

        return warehouseMapper.toResponse(updatedWarehouse);
    }

    @Override
    public void activateWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found with id : " + id));

        warehouse.setStatus(WarehouseStatus.ACTIVE);

        warehouseRepository.save(warehouse);

        auditLogService.log(
                "ACTIVATE",
                "Warehouse",
                warehouse.getId(),
                "SYSTEM",
                warehouse.getWarehouseCode(),
                "Warehouse Activated"
        );
    }

    @Override
    public void deactivateWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found with id : " + id));

        warehouse.setStatus(WarehouseStatus.INACTIVE);

        warehouseRepository.save(warehouse);

        auditLogService.log(
                "DEACTIVATE",
                "Warehouse",
                warehouse.getId(),
                "SYSTEM",
                warehouse.getWarehouseCode(),
                "Warehouse Deactivated"
        );
    }

    @Override
    public void deleteWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found with id : " + id));

        warehouse.setDeleted(true);
        warehouse.setStatus(WarehouseStatus.INACTIVE);

        warehouseRepository.save(warehouse);

        auditLogService.log(
                "DELETE",
                "Warehouse",
                warehouse.getId(),
                "SYSTEM",
                warehouse.getWarehouseCode(),
                "Warehouse Soft Deleted"
        );
    }
}