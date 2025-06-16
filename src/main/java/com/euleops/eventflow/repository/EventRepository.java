package com.euleops.eventflow.repository;

import com.euleops.eventflow.domain.Event;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface EventRepository extends Neo4jRepository<Event, Long> {
    Optional<Event> findByEventId(String eventId);
}

