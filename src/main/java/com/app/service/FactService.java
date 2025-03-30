package com.app.service;

import com.app.mapper.FactMapper;
import com.app.model.Fact;
import com.app.repository.FactRepository;
import com.app.web.dto.FactResponse;
import com.app.web.dto.NewFactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.app.mapper.FactMapper.toFact;
import static com.app.mapper.FactMapper.toFactResponse;

@Service
@RequiredArgsConstructor
public class FactService {
    private final FactRepository factRepository;


    public List<FactResponse> getAllFacts() {
        return factRepository.findAll().stream().map(FactMapper::toFactResponse).toList();
    }

    public Fact createFact(NewFactRequest factRequest) {

            return factRepository.save(toFact(factRequest));
    }

    public FactResponse getFactById(UUID id) {
        Fact fact = this.factRepository.findById(id).orElseThrow();
        return toFactResponse(fact);
    }
}
