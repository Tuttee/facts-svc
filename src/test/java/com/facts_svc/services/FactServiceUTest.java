package com.facts_svc.services;

import com.facts_svc.model.Fact;
import com.facts_svc.repository.FactRepository;
import com.facts_svc.service.FactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FactServiceUTest {
    @Mock
    private FactRepository factRepository;

    @InjectMocks
    private FactService factService;

    @Test
    void givenExistingFactsInDb_whenGetAll_thenReturnListOfFacts() {
        List<Fact> facts = List.of(new Fact(), new Fact());

        when(factRepository.findAll()).thenReturn(facts);

        assertThat(factService.getAllFacts()).hasSize(2);
    }

    @Test
    void givenCorrectData_whenCreateFact_thenSuccess() {
        Fact fact = new Fact();
        when(factRepository.save(any(Fact.class))).thenReturn(fact);

        Fact fact1 = factService.createFact(fact);

        verify(factRepository, times(1)).save(any(Fact.class));
        assertEquals(fact, fact1);
    }

    @Test
    void givenFactIdInvalid_whenGetFactById_thenThrowException() {
        when(factRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> factService.getFactById(any()));
    }

    @Test
    void givenFactIdValid_whenGetFactById_thenReturnsFact() {
        Fact fact = new Fact(UUID.randomUUID(), "Test", LocalDateTime.now(), UUID.randomUUID());

        when(factRepository.findById(fact.getId())).thenReturn(Optional.of(fact));

        Fact factById = factService.getFactById(fact.getId());

        verify(factRepository, times(1)).findById(fact.getId());
        assertEquals(fact, factById);
    }

}
