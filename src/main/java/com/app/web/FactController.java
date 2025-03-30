package com.app.web;

import com.app.model.Fact;
import com.app.service.FactService;
import com.app.web.dto.FactResponse;
import com.app.web.dto.NewFactRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.app.mapper.FactMapper.toFactResponse;
import static com.app.web.Paths.API_V1_BASE_PATH;

@RestController
@RequestMapping(API_V1_BASE_PATH + "/facts")
@Tag(name = "Facts Management", description = "Operation related to Facts")
public class FactController {
    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping
    public ResponseEntity<List<FactResponse>> getAllFacts() {
        List<FactResponse> factResponses = factService
                .getAllFacts();

        if (factResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(factResponses);

    }

    @GetMapping("/random")
    public ResponseEntity<FactResponse> getRandomFact() {
        List<FactResponse> allFacts = this.factService.getAllFacts();

        if (allFacts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Random random = new Random();
        FactResponse factResponse = allFacts.get(random.nextInt(allFacts.size()));
        return ResponseEntity.ok(factResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactResponse> getFactById(@PathVariable UUID id) {
        FactResponse factById = factService.getFactById(id);

        return ResponseEntity.ok(factById);
    }

    @PostMapping
    public ResponseEntity<FactResponse> createFact(@RequestBody @Valid NewFactRequest newFactRequest) {
        Fact fact = this.factService.createFact(newFactRequest);

        FactResponse factResponse = toFactResponse(fact);

        return ResponseEntity.created(URI.create(API_V1_BASE_PATH + "/facts/" + fact.getId()))
                .body(factResponse);
    }
}
