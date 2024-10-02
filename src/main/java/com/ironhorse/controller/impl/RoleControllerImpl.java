package com.ironhorse.controller.impl;

import com.ironhorse.controller.RoleController;
import com.ironhorse.dto.RoleDto;
import com.ironhorse.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    @Override
    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.findAll());
    }
}