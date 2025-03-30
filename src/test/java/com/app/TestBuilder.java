package com.app;

import com.app.model.Fact;
import com.app.web.dto.FactResponse;
import com.app.web.dto.NewFactRequest;
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
