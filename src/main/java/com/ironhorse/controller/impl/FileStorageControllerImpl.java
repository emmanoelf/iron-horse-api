package com.ironhorse.controller.impl;

import com.ironhorse.controller.FileStorageController;
import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/users/upload")
public class FileStorageControllerImpl implements FileStorageController {
    private final FileStorageService fileStorageService;

    public FileStorageControllerImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> uploadFile(MultipartFile file, @PathVariable Long userId) {
        this.fileStorageService.uploadFile(file, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUserProfileFile(@PathVariable Long userId) {
        this.fileStorageService.deleteUserProfileFile(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FileStorageDto> getUserProfile(@PathVariable Long userId) {
        FileStorageDto fileStorageDto = this.fileStorageService.getUserProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageDto);
    }
}
