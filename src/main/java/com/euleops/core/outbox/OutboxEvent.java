package com.euleops.core.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Entity
@Table(name = "outbox_event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private Long id;
    private String aggregateId;
    private String eventType;

    @Lob
    private String payload;

    private boolean published;
    private Instant createdAt;

}
