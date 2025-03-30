package com.app.event;

import com.app.event.payload.FactAddedEvent;
import com.app.service.FactService;
import com.app.web.dto.NewFactRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FactAddedConsumerEvent {

    private final FactService factService;

    public FactAddedConsumerEvent(FactService factService) {
        this.factService = factService;
    }

    @KafkaListener(topics = "fact-added-event.v1", groupId = "facts-svc")
    public void consumeFactAddedEvent(FactAddedEvent factAddedEvent) {
        log.info("FactAddedEvent received to fact-added-event.v1 from user [%s] with content [%s].".formatted(factAddedEvent.getAddedBy(), factAddedEvent.getContent()));

        NewFactRequest newFactRequest = NewFactRequest.builder()
                .addedBy(factAddedEvent.getAddedBy())
                .content(factAddedEvent.getContent()).build();

        factService.createFact(newFactRequest);
    }
}
