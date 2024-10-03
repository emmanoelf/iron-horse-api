package com.ironhorse.controller.impl;

import com.ironhorse.controller.UserInfoController;
import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.service.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user_info")
public class UserInfoControllerImpl implements UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoControllerImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserInfoResponseDto> save(@RequestBody @Valid UserInfoDto userInfoDto, @PathVariable Long userId) {
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.save(userInfoDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoResponseDto);
    }

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserInfoResponseDto> findByUserId(@PathVariable Long userId) {
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponseDto);
    }
}
