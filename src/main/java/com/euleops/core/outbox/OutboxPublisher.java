package com.euleops.core.outbox;

import com.euleops.core.outbox.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository repo;
    private final KafkaTemplate<String, String> kafka;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void publish() {
        repo.findTop100ByPublishedFalseOrderByCreatedAtAsc()
                .forEach(ev -> {
                    kafka.send(ev.getEventType(), ev.getAggregateId(), ev.getPayload());
                    ev.setPublished(true);
                });
    }
}

