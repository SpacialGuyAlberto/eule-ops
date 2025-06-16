package com.euleops.supplychain.service;

import com.euleops.supplychain.domain.Lot;
import com.euleops.supplychain.domain.Supplier;
import com.euleops.supplychain.dto.LotDTO;
import com.euleops.supplychain.mapper.LotMapper;
import com.euleops.supplychain.repository.LotRepository;
import com.euleops.supplychain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LotService {

    private final LotRepository lotRepo;
    private final SupplierRepository supplierRepo;
    private final LotMapper mapper;

    @Transactional
    public LotDTO createLot(LotDTO dto) {
        Lot lot = mapper.toEntity(dto);

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepo.findBySupplierId(dto.getSupplierId())
                    .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
            lot.setSupplier(supplier);
        }

        Lot saved = lotRepo.save(lot);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public LotDTO getByLotId(String lotId) {
        Lot lot = lotRepo.findByLotId(lotId)
                .orElseThrow(() -> new IllegalArgumentException("Lot not found"));
        return mapper.toDto(lot);
    }
}
