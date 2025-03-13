package com.facts_svc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class NewFactRequest {

    @Size(min = 5)
    private String content;

    @NotNull
    private UUID addedBy;
}
