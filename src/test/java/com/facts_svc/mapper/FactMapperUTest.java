package com.facts_svc.mapper;

import com.facts_svc.model.Fact;
import com.facts_svc.web.dto.FactResponse;
import com.facts_svc.web.dto.NewFactRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FactMapperUTest {

    @Test
    void givenHappyPath_whenMappingToFactResponse() {
        Fact fact = Fact.builder()
                .content("Test content")
                .id(UUID.randomUUID())
                .addedBy(UUID.randomUUID())
                .created(LocalDateTime.now())
                .build();

        FactResponse factResponse = FactMapper.toFactResponse(fact);

        assertEquals(fact.getContent(), factResponse.getContent());
    }

    @Test
    void givenHappyPath_whenMappingToFact() {
        NewFactRequest newFactRequest = NewFactRequest.builder()
                .content("Test content")
                .addedBy(UUID.randomUUID())
                .build();

        Fact fact = FactMapper.toFact(newFactRequest);

        assertEquals(newFactRequest.getContent(), fact.getContent());
        assertEquals(newFactRequest.getAddedBy(), fact.getAddedBy());
    }
}
