package com.euleops.supplychain.controller;

import com.euleops.supplychain.dto.LotDTO;
import com.euleops.supplychain.service.LotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lots")
@RequiredArgsConstructor
public class LotController {

    private final LotService lotService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LotDTO create(@Valid @RequestBody LotDTO dto) {
        return lotService.createLot(dto);
    }

    @GetMapping("/{lotId}")
    public LotDTO find(@PathVariable String lotId) {
        return lotService.getByLotId(lotId);
    }
}
