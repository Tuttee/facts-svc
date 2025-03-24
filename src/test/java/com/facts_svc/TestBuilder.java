package com.facts_svc;

import com.facts_svc.model.Fact;
import com.facts_svc.web.dto.FactResponse;
import com.facts_svc.web.dto.NewFactRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class TestBuilder {

    public static FactResponse getFactResponse() {
        return new FactResponse("Test Fact");
    }

    public static NewFactRequest getNewFactRequest() {
        return NewFactRequest.builder()
                .content("TestContent")
                .addedBy(UUID.randomUUID())
                .build();
    }

    public static Fact getFact() {
        return Fact.builder()
                .content("TestContent")
                .addedBy(UUID.randomUUID())
                .created(LocalDateTime.now())
                .id(UUID.randomUUID())
                .build();
    }
}
