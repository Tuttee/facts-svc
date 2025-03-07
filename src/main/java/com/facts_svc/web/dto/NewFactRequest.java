package com.facts_svc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class NewFactRequest {
    @NotBlank
    private String content;

    @NotNull
    private UUID addedBy;
}
