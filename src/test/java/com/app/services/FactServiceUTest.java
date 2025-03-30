package com.app.services;

import com.app.model.Fact;
import com.app.repository.FactRepository;
import com.app.service.FactService;
import com.app.web.dto.FactResponse;
import com.app.web.dto.NewFactRequest;
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

import static com.app.TestBuilder.getNewFactRequest;
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
        NewFactRequest factRequest = getNewFactRequest();
        when(factRepository.save(any(Fact.class))).thenReturn(fact);

        Fact fact1 = factService.createFact(factRequest);

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

        FactResponse factById = factService.getFactById(fact.getId());

        verify(factRepository, times(1)).findById(fact.getId());
        assertEquals(fact.getContent(), factById.getContent());
    }

}
