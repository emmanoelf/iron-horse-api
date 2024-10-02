package com.ironhorse.service;

import com.ironhorse.dto.RoleDto;
import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();
}