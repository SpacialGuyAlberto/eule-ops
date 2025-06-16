package com.euleops.eventflow.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class EventDTO {
    private String eventId;
    private String lotId;
    private String opCode;
    private String stationId;
    private Instant timestamp;
}
