package com.app.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NewFactRequest {

    @Size(min = 5)
    private String content;

    @NotNull
    private UUID addedBy;
}
