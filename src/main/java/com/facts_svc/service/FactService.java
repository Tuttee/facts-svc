package com.facts_svc.service;

import com.facts_svc.mapper.FactMapper;
import com.facts_svc.model.Fact;
import com.facts_svc.repository.FactRepository;
import com.facts_svc.web.dto.FactResponse;
import com.facts_svc.web.dto.NewFactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.facts_svc.mapper.FactMapper.toFact;
import static com.facts_svc.mapper.FactMapper.toFactResponse;

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
