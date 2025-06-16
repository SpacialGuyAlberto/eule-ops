package com.euleops.supplychain.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Node("Lot")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Lot {

    @Id @GeneratedValue
    private Long id;

    private String lotId;
    private String material;
    private Double weightKg;
    private Instant createdAt;

    @Relationship(type = "SUPPLIED_BY", direction = Relationship.Direction.OUTGOING)
    private Supplier supplier;

    @Relationship(type = "HAS_EVENT", direction = Relationship.Direction.OUTGOING)
    private List<com.euleops.eventflow.domain.Event> events = new ArrayList<>();
}

