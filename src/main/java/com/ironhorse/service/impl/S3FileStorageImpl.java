package com.ironhorse.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.exception.FileStorageNotFoundException;
import com.ironhorse.exception.UserInfoNotFoundException;
import com.ironhorse.mapper.FileStorageMapper;
import com.ironhorse.model.FileStorage;
import com.ironhorse.model.UserInfo;
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

    private static final String CONTENT_PNG = "image/png";
    private static final String CONTENT_JPEG = "image/jpeg";
    private static final String PREFIX_FILENAME = "profile";

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

    @Override
    public void uploadCarImagesFiles(List<MultipartFile> files, Long carId) {

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

    }

    @Override
    public List<FileStorageDto> getCarImages(Long id) {
        return List.of();
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
