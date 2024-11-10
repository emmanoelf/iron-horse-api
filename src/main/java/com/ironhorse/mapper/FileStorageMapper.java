package com.ironhorse.mapper;

import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.model.FileStorage;

public class FileStorageMapper {
    public static FileStorageDto toDto(FileStorage fileStorage) {
        return new FileStorageDto(
                fileStorage.getName(),
                fileStorage.getPath(),
                fileStorage.getSize(),
                fileStorage.getCreated_at());
    }
}
