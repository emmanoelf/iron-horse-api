package com.ironhorse.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.exception.FileStorageNotFoundException;
import com.ironhorse.exception.UserInfoNotFoundException;
import com.ironhorse.mapper.CarImageMapper;
import com.ironhorse.mapper.FileStorageMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.CarImages;
import com.ironhorse.model.FileStorage;
import com.ironhorse.model.UserInfo;
import com.ironhorse.repository.CarImagesRepository;
import com.ironhorse.repository.FileStorageRepository;
import com.ironhorse.repository.UserInfoRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class S3FileStorageImpl implements FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    private final UserInfoRepository userInfoRepository;
    private final AmazonS3 amazonS3;
    private final AuthenticatedService authenticatedService;
    private final CarImagesRepository carImagesRepository;

    private static final String CONTENT_PNG = "image/png";
    private static final String CONTENT_JPEG = "image/jpeg";
    private static final String PREFIX_FILENAME = "profile";
    private static final String PREFIX_CARIMAGEFILE = "car_image";
    private static final int MAX_FILES = 7;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    @Transactional
    public void uploadFile(MultipartFile multiPartFile) {
        try {
            Long userId = this.authenticatedService.getCurrentUserId();
            this.validateFile(multiPartFile);

            UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                    () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

            if (userInfo.getUserPicture() != null) {
                this.deleteUserProfileFile();
            }

            String fileName = this.generateFilename(multiPartFile.getOriginalFilename());
            File file = this.convertMultipartToFile(multiPartFile);
            amazonS3.putObject(bucketName, fileName, file);
            file.delete();
            String urlImage = amazonS3.getUrl(bucketName, fileName).toString();

            FileStorage fileStorage = this.createFileStorage(fileName, urlImage, multiPartFile.getSize());
            userInfo.setUserPicture(fileStorage);

            this.fileStorageRepository.save(fileStorage);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar arquivo para S3", e);
        }
    }

    @Transactional
    @Override
    public void uploadCarImagesFiles(List<MultipartFile> files, Long carId) {
        try {
            this.validateFiles(files);

            Long userId = this.authenticatedService.getCurrentUserId();
            UserInfo userInfo = this.userInfoRepository.findByUserId(userId)
                    .orElseThrow(() -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

            Car car = userInfo.getUser().getCars().stream()
                    .filter(c -> c.getId().equals(carId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Carro não encontrado"));

            if (car.getCarInfo() != null &&
                    car.getCarInfo().getCarImages() != null &&
                    !car.getCarInfo().getCarImages().isEmpty()) {
                this.deleteCarImageFile(carId);
            }

            if (files.size() > MAX_FILES) {
                throw new FileUploadException("Serão necessárias no máximo " + MAX_FILES + " imagens, você forneceu " + files.size());
            }

            for (MultipartFile file : files){
                String fileName = this.generateCarImageFileName(file.getOriginalFilename());
                File fileImage = this.convertMultipartToFile(file);
                amazonS3.putObject(bucketName,fileName, fileImage);
                fileImage.delete();
                String urlImage = amazonS3.getUrl(bucketName, fileName).toString();
                CarImages carImagesStorage = this.createImageStorage(fileName, urlImage, file.getSize());
                carImagesStorage.setCarInfo(car.getCarInfo());
                this.carImagesRepository.save(carImagesStorage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload das imagens", e);
        }
    }

    @Override
    @Transactional
    public void deleteUserProfileFile() {
        Long userId = this.authenticatedService.getCurrentUserId();

        UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        if (userInfo.getUserPicture() == null) {
            throw new IllegalArgumentException("Não há foto de perfil para ser deletada");
        }

        FileStorage fileStorage = this.fileStorageRepository.findById(userInfo.getUserPicture().getId()).orElseThrow(
                () -> new FileStorageNotFoundException("Arquivo não encontrado"));

        amazonS3.deleteObject(bucketName, fileStorage.getName());

        userInfo.setUserPicture(null);
        this.fileStorageRepository.deleteById(fileStorage.getId());
        this.fileStorageRepository.flush();
    }

    @Override
    public void deleteCarImageFile(Long id) {
        Long userId = authenticatedService.getCurrentUserId();
        UserInfo userInfo = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        Car car = userInfo.getUser().getCars().stream()
                .filter(carro -> carro.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não há carros para excluir suas imagens"));

        List<CarImages> carImagesList = carImagesRepository.findByCarInfoId(car.getId());

        if (carImagesList.isEmpty()) {
            throw new FileStorageNotFoundException("Nenhuma imagem encontrada para excluir.");
        }

        for (CarImages carImage : carImagesList) {
            amazonS3.deleteObject(bucketName, carImage.getName());
            carImagesRepository.delete(carImage);
        }
        car.getCarInfo().setCarImages(null);
        carImagesRepository.flush();
    }

    @Override
    public void deleteOnlyFromStorage(Car car) {
        List<CarImages> carImagesList = car.getCarInfo().getCarImages();

        for (CarImages carImage : carImagesList) {
            amazonS3.deleteObject(bucketName, carImage.getName());
        }
    }

    @Override
    public List<FileStorageDto> getCarImages(Long id) {
        Long userId = this.authenticatedService.getCurrentUserId();

        UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        Car car = userInfo.getUser().getCars().stream()
                .filter(carro -> carro.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não há carros para excluir suas imagens"));

        List<CarImages> carImagesList = carImagesRepository.findByCarInfoId(car.getId());

        if (carImagesList.isEmpty()) {
            throw new FileStorageNotFoundException("Nenhuma imagem encontrada para excluir.");
        }

        return CarImageMapper.toCarDTOList(carImagesList);
    }

    @Override
    public FileStorageDto getUserProfile() {
        Long userId = this.authenticatedService.getCurrentUserId();

        UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        if (userInfo.getUserPicture() == null) {
            throw new IllegalArgumentException("O usuário não possui foto");
        }

        FileStorage fileStorage = this.fileStorageRepository.findById(userInfo.getUserPicture().getId()).orElseThrow(
                () -> new FileStorageNotFoundException("Arquivo não encontrado"));

        return FileStorageMapper.toDto(fileStorage);
    }

    private void validateFiles(List<MultipartFile> files) throws FileUploadException {
        if (files.isEmpty()) {
            throw new FileUploadException("Arquivo não encontrado");
        }
    }

    private String generateCarImageFileName(String originalFilename){
        long timestamp = System.currentTimeMillis();
        return String.format("%s_%d_%s", PREFIX_CARIMAGEFILE, timestamp, originalFilename);
    }

    private String generateFilename(String originalFilename) {
        long timestamp = System.currentTimeMillis();
        return String.format("%s_%d_%s", PREFIX_FILENAME, timestamp, originalFilename);
    }

    private boolean isValidContentType(String contentType) {
        return CONTENT_PNG.equals(contentType) || CONTENT_JPEG.equals(contentType);
    }

    private void validateFile(MultipartFile file) throws FileUploadException {
        if (file.isEmpty()) {
            throw new FileUploadException("Arquivo não encontrado");
        }

        if (!this.isValidContentType(file.getContentType())) {
            throw new FileUploadException("A imagem deve ser PNG ou JPEG");
        }
    }

    private CarImages createImageStorage(String fileName, String absolutePath, Long size){
        CarImages carImages = new CarImages();
        carImages.setName(fileName);
        carImages.setPath(absolutePath);
        carImages.setSize(size);

        return carImages;
    }

    private FileStorage createFileStorage(String fileName, String absolutePath, Long size) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(fileName);
        fileStorage.setPath(absolutePath);
        fileStorage.setSize(size);
        return fileStorage;
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
