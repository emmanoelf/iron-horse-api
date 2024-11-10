package com.ironhorse.mapper;

import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.model.CarImages;
import com.ironhorse.model.FileStorage;


import java.util.List;
import java.util.stream.Collectors;

public class CarImageMapper {
    public static FileStorageDto toDto(CarImages carImages) {
        return new FileStorageDto(
                carImages.getName(),
                carImages.getPath(),
                carImages.getSize(),
                carImages.getCreated_at());
    }

    public static List<FileStorageDto> toCarDTOList(List<CarImages> carImages) {
        return carImages.stream()
                .map(CarImageMapper::toDto)
                .collect(Collectors.toList());
    }

    public static FileStorage toModel(CarImages carImages) {
        return FileStorage.builder()
                .path(carImages.getPath())
                .size(carImages.getSize())
                .name(carImages.getName())
                .build();
    }

    public static List<FileStorage> toModelList(List<CarImages> carImagesList) {
        return carImagesList.stream()
                .map(carImages -> FileStorage.builder()
                        .path(carImages.getPath())
                        .size(carImages.getSize())
                        .name(carImages.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
