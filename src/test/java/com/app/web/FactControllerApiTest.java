package com.app.web;

import com.app.model.Fact;
import com.app.service.FactService;
import com.app.web.dto.FactResponse;
import com.app.web.dto.NewFactRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.app.TestBuilder.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FactController.class)
public class FactControllerApiTest {
    @MockitoBean
    private FactService factService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFacts_happyPath() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/facts");

        FactResponse factResponse = getFactResponse();
        when(factService.getAllFacts()).thenReturn(List.of(factResponse));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(factService, times(1)).getAllFacts();
    }

    @Test
    void getAllFacts_whenNoFactsArePresent_thenReturnsNoContent() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/facts");

        when(factService.getAllFacts()).thenReturn(new ArrayList<>());

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        verify(factService, times(1)).getAllFacts();
    }

    @Test
    void getRandomFact_happyPath() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/facts/random");

        FactResponse factResponse = getFactResponse();
        when(factService.getAllFacts()).thenReturn(List.of(factResponse));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isNotEmpty());
        verify(factService, times(1)).getAllFacts();
    }

    @Test
    void getRandomFact_whenNoFactsArePresent_thenReturnsNoContent() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/facts/random");

        when(factService.getAllFacts()).thenReturn(new ArrayList<>());

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        verify(factService, times(1)).getAllFacts();
    }

    @Test
    void getFactById_happyPath() throws Exception {
        UUID factId = UUID.randomUUID();
        MockHttpServletRequestBuilder request = get("/api/v1/facts/" + factId);
        FactResponse factResponse = getFactResponse();

        when(factService.getFactById(factId)).thenReturn(factResponse);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isNotEmpty());
        verify(factService, times(1)).getFactById(factId);
    }

    @Test
    void getFactById_whenIdIsInvalid_thenReturn404AndNotFound() throws Exception {
        UUID factId = UUID.randomUUID();
        MockHttpServletRequestBuilder request = get("/api/v1/facts/" + factId);

        when(factService.getFactById(factId)).thenThrow(new NoSuchElementException());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty());
        verify(factService, times(1)).getFactById(factId);
    }

    @Test
    void givenAnyException_thenReturn404AndNotFound() throws Exception {
        UUID factId = UUID.randomUUID();
        MockHttpServletRequestBuilder request = get("/api/v1/facts/" + factId);

        when(factService.getFactById(factId)).thenThrow(new RuntimeException());

        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("status").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty());
        verify(factService, times(1)).getFactById(factId);
    }

    @Test
    void postWithBodyToCreateFact_returns201AndCorrectDtoStructure() throws Exception {
        NewFactRequest newFactRequest = getNewFactRequest();
        Fact fact = getFact();

        when(factService.createFact(newFactRequest)).thenReturn(fact);

        MockHttpServletRequestBuilder request = post("/api/v1/facts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(newFactRequest));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("content").isNotEmpty());
    }

    @Test
    void postWithBodyToCreateFact_returns422AndError() throws Exception {
        NewFactRequest newFactRequest = getNewFactRequest();

        when(factService.createFact(newFactRequest)).thenThrow(new DataIntegrityViolationException("error"));

        MockHttpServletRequestBuilder request = post("/api/v1/facts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(newFactRequest));

        mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("status").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty());
    }


}
