package com.euleops.supplychain.repository;

import com.euleops.supplychain.domain.Supplier;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface SupplierRepository extends Neo4jRepository<Supplier, Long> {
    Optional<Supplier> findBySupplierId(String supplierId);
}

