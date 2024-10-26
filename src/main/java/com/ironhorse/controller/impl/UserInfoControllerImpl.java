package com.ironhorse.controller.impl;

import com.ironhorse.controller.UserInfoController;
import com.ironhorse.dto.UserInfoCreateDto;
import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userInfo")
public class UserInfoControllerImpl implements UserInfoController {

    private final UserInfoService userInfoService;
    private final AuthenticatedService authenticatedService;

    public UserInfoControllerImpl(UserInfoService userInfoService, AuthenticatedService authenticatedService) {
        this.userInfoService = userInfoService;
        this.authenticatedService = authenticatedService;
    }

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserInfoResponseDto> save(@RequestBody @Valid UserInfoCreateDto userInfoCreateDto) {
        Long userId = this.authenticatedService.getCurrentUserId();
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.save(userInfoCreateDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoResponseDto);
    }

    @Override
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserInfoResponseDto> findByUserId() {
        Long userId = this.authenticatedService.getCurrentUserId();
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponseDto);
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Long userId) {
        this.userInfoService.deleteByUserId(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping()
    public ResponseEntity<UserInfoResponseDto> update(@RequestBody @Valid UserInfoDto userInfoDto) {
        Long userId = this.authenticatedService.getCurrentUserId();
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.update(userInfoDto, userId);
        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponseDto);
    }
}
