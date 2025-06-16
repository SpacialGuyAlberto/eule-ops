package com.euleops.supplychain.controller;

import com.euleops.supplychain.dto.SupplierDTO;
import com.euleops.supplychain.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDTO create(@Valid @RequestBody SupplierDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{supplierId}")
    public SupplierDTO find(@PathVariable String supplierId) {
        return service.find(supplierId);
    }
}
