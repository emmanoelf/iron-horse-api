package com.ironhorse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemDetail {
    private Integer status;
    private String type;
    private String title;
    private String detail;
}
