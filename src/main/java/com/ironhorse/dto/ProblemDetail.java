package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {
    private Integer status;
    private String type;
    private String title;
    private String detail;
    private List<ProblemObject> problemObjects;
}
