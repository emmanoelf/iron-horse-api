package com.ironhorse.mapper;

import com.ironhorse.dto.RoleDto;
import com.ironhorse.model.Role;

public class RoleMapper {
    public static Role toModel(RoleDto roleDTO) {
        return Role.builder()
                .name(roleDTO.name())
                .build();
    }
    public static RoleDto toDto(Role role) {
        return new RoleDto(role.getName());
    }
}