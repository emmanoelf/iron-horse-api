package com.ironhorse.service.impl;

import com.ironhorse.dto.RoleDto;
import com.ironhorse.mapper.RoleMapper;
import com.ironhorse.model.Role;
import com.ironhorse.repository.RoleRepository;
import com.ironhorse.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles
                .stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }
}