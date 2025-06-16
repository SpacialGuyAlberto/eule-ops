package com.euleops.supplychain.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class LotDTO {
    private String lotId;
    private String material;
    private Double weightKg;
    private Instant createdAt;
    private String supplierId;
}
