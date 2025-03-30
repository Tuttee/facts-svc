package com.app.mapper;

import com.app.model.Fact;
import com.app.web.dto.FactResponse;
import com.app.web.dto.NewFactRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class FactMapper {
    public static FactResponse toFactResponse(Fact fact) {
        return FactResponse
                .builder()
                .content(fact.getContent())
                .build();
    }

    public static Fact toFact(NewFactRequest newFactRequest) {
        Fact fact = new Fact();
        fact.setContent(newFactRequest.getContent());
        fact.setCreated(LocalDateTime.now());
        fact.setAddedBy(newFactRequest.getAddedBy());
        return fact;
    }
}
