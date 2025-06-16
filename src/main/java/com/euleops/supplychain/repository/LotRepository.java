package com.euleops.supplychain.repository;

import com.euleops.supplychain.domain.Lot;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface LotRepository extends Neo4jRepository<Lot, Long> {
    Optional<Lot> findByLotId(String lotId);
}

