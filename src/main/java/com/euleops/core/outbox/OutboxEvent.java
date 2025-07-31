package com.euleops.core.outbox;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_event", schema = "core")
@Getter
@Setter
public class OutboxEvent {

    @jakarta.persistence.Id
    @Id
    private UUID id;

    private String aggregateId;
    @Column(nullable = false, length = 120)
    private String aggregateType;

    private String type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private JsonNode payload;           // o Map<String,Object>

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    private Boolean published;

}
