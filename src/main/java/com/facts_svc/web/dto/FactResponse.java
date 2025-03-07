package com.facts_svc.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FactResponse {
    private String content;
}
