package com.euleops.supplychain.service;

import com.euleops.supplychain.dto.SupplierDTO;
import com.euleops.supplychain.mapper.SupplierMapper;
import com.euleops.supplychain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository repo;
    private final SupplierMapper mapper;

    @Transactional
    public SupplierDTO create(SupplierDTO dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @Transactional(readOnly = true)
    public SupplierDTO find(String supplierId) {
        var supplier = repo.findBySupplierId(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
        return mapper.toDto(supplier);
    }
}
