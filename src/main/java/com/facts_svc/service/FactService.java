package com.facts_svc.service;

import com.facts_svc.model.Fact;
import com.facts_svc.repository.FactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FactService {
    private final FactRepository factRepository;

    public List<Fact> getAllFacts() {
        return factRepository.findAll();
    }

    public Fact createFact(Fact fact) {
            return factRepository.save(fact);
    }

    public Fact getFactById(UUID id) {
        return this.factRepository.findById(id).orElseThrow();
    }
}
