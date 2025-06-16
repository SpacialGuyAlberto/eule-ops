package com.euleops.supplychain.mapper;

import com.euleops.supplychain.domain.Supplier;
import com.euleops.supplychain.dto.SupplierDTO;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierDTO dto) {
        return Supplier.builder()
                .supplierId(dto.getSupplierId())
                .name(dto.getName())
                .country(dto.getCountry())
                .build();
    }

    public SupplierDTO toDto(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setSupplierId(supplier.getSupplierId());
        dto.setName(supplier.getName());
        dto.setCountry(supplier.getCountry());
        return dto;
    }
}
