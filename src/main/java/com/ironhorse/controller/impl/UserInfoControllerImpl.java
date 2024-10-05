package com.ironhorse.controller.impl;

import com.ironhorse.controller.UserInfoController;
import com.ironhorse.dto.UserInfoCreateDto;
import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.service.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userInfo")
public class UserInfoControllerImpl implements UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoControllerImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserInfoResponseDto> save(@RequestBody @Valid UserInfoCreateDto userInfoCreateDto, @PathVariable Long userId) {
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.save(userInfoCreateDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoResponseDto);
    }

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserInfoResponseDto> findByUserId(@PathVariable Long userId) {
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
    @PutMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> update(@RequestBody @Valid UserInfoDto userInfoDto,
                                                      @PathVariable Long userId) {
        UserInfoResponseDto userInfoResponseDto = this.userInfoService.update(userInfoDto, userId);
        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponseDto);
    }
}
