package com.euleops.eventflow.service;

import com.euleops.eventflow.domain.Event;
import com.euleops.eventflow.dto.EventDTO;
import com.euleops.eventflow.repository.EventRepository;
import com.euleops.supplychain.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepo;
    private final LotRepository lotRepo;
    private final KafkaTemplate<String, EventDTO> kafkaTemplate;

    private static final String TOPIC_EVENTS_RAW = "events.raw";

    @Transactional
    public void register(EventDTO dto) {
        var lot = lotRepo.findByLotId(dto.getLotId())
                .orElseThrow(() -> new IllegalArgumentException("Lot not found"));

        var ev = Event.builder()
                .eventId(dto.getEventId())
                .opCode(dto.getOpCode())
                .timestamp(dto.getTimestamp())
                .stationId(dto.getStationId())
                .lot(lot)
                .build();

        eventRepo.save(ev);

        // enviar a Kafka (key = lotId)
        kafkaTemplate.send(TOPIC_EVENTS_RAW, dto.getLotId(), dto);
    }
}
