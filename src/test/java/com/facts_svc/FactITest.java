package com.facts_svc;

import com.facts_svc.model.Fact;
import com.facts_svc.repository.FactRepository;
import com.facts_svc.service.FactService;
import com.facts_svc.web.dto.NewFactRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FactITest {
    @Autowired
    private FactService factService;

    @Autowired
    private FactRepository factRepository;

    @Test
    void addFact_HappyPath() {
        UUID addedBy = UUID.randomUUID();
        NewFactRequest newFactRequest = NewFactRequest.builder()
                .content("Test Content")
                .addedBy(addedBy).build();

        factService.createFact(newFactRequest);

        Optional<Fact> fact = factRepository.findByContent(newFactRequest.getContent());

        assertTrue(fact.isPresent());
        assertEquals(newFactRequest.getContent(), fact.get().getContent());
        assertEquals(addedBy, fact.get().getAddedBy());
    }

}
