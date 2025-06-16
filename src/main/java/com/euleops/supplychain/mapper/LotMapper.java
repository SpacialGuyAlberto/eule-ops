package com.euleops.supplychain.mapper;

import com.euleops.supplychain.domain.Lot;
import com.euleops.supplychain.dto.LotDTO;
import org.springframework.stereotype.Component;

@Component
public class LotMapper {

    public Lot toEntity(LotDTO dto) {
        return Lot.builder()
                .lotId(dto.getLotId())
                .material(dto.getMaterial())
                .weightKg(dto.getWeightKg())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    public LotDTO toDto(Lot lot) {
        LotDTO dto = new LotDTO();
        dto.setLotId(lot.getLotId());
        dto.setMaterial(lot.getMaterial());
        dto.setWeightKg(lot.getWeightKg());
        dto.setCreatedAt(lot.getCreatedAt());
        if (lot.getSupplier() != null) {
            dto.setSupplierId(lot.getSupplier().getSupplierId());
        }
        return dto;
    }
}
