package com.ironhorse.controller.impl;

import com.ironhorse.controller.FileStorageController;
import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.service.impl.FileLocalStorageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/users/upload")
public class FileStorageControllerImpl implements FileStorageController {
    private final FileLocalStorageServiceImpl fileLocalStorageService;

    public FileStorageControllerImpl(FileLocalStorageServiceImpl fileLocalStorageService) {
        this.fileLocalStorageService = fileLocalStorageService;
    }

    @Override
    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> uploadFile(MultipartFile file, @PathVariable Long userId) {
        this.fileLocalStorageService.uploadFile(file, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUserProfileFile(@PathVariable Long userId) {
        this.fileLocalStorageService.deleteUserProfileFile(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FileStorageDto> getUserProfile(@PathVariable Long userId) {
        FileStorageDto fileStorageDto = this.fileLocalStorageService.getUserProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageDto);
    }
}
