package com.euleops.eventflow.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.time.Instant;

@Node("Event")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Event {

    @Id @GeneratedValue
    private Long id;

    private String eventId;
    private String opCode;
    private Instant timestamp;
    private String stationId;

    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.INCOMING)
    private com.euleops.supplychain.domain.Lot lot;
}

