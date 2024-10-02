package com.ironhorse.controller;

import com.ironhorse.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Tag(name = "Role Controller")
public interface RoleController {

    @Operation(summary = "Find Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Find All Roles",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity <List<RoleDto>> findAll();
}