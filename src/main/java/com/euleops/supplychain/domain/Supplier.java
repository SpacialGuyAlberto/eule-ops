package com.euleops.supplychain.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Node("Supplier")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Supplier {

    @Id @GeneratedValue
    private Long id;

    private String supplierId;
    private String name;
    private String country;

    @Relationship(type = "SUPPLIES", direction = Relationship.Direction.INCOMING)
    private List<Lot> suppliedLots = new ArrayList<>();
}
