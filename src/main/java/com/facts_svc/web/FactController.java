package com.facts_svc.web;

import com.facts_svc.mapper.FactMapper;
import com.facts_svc.model.Fact;
import com.facts_svc.service.FactService;
import com.facts_svc.web.dto.FactResponse;
import com.facts_svc.web.dto.NewFactRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.facts_svc.mapper.FactMapper.toFact;
import static com.facts_svc.mapper.FactMapper.toFactResponse;
import static com.facts_svc.web.Paths.API_V1_BASE_PATH;

@RestController
@RequestMapping(API_V1_BASE_PATH + "/facts")
public class FactController {
    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping
    public ResponseEntity<List<FactResponse>> getAllFacts() {
        List<FactResponse> factResponses = factService
                .getAllFacts()
                .stream()
                .map(FactMapper::toFactResponse)
                .toList();

        if (factResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(factResponses);

    }

    @GetMapping("/random")
    public ResponseEntity<FactResponse> getRandomFact() {
        List<Fact> allFacts = this.factService.getAllFacts();

        if (allFacts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Random random = new Random();
        FactResponse factResponse = toFactResponse(allFacts.get(random.nextInt(allFacts.size())));
        return ResponseEntity.ok(factResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactResponse> getFactById(@PathVariable UUID id) {
        Fact factById = factService.getFactById(id);

            return ResponseEntity.ok(toFactResponse(factById));
    }

    @PostMapping
    public ResponseEntity<FactResponse> createFact(@RequestBody @Valid NewFactRequest newFactRequest) {

        Fact fact = this.factService.createFact(toFact(newFactRequest));

        FactResponse factResponse = toFactResponse(fact);

        return ResponseEntity.created(URI.create(API_V1_BASE_PATH + "/facts/" + fact.getId()))
                .body(factResponse);
    }
}
