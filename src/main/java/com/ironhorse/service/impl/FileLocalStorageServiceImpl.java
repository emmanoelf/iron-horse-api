package com.ironhorse.service.impl;

import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.exception.FileStorageNotFoundException;
import com.ironhorse.exception.UserInfoNotFoundException;
import com.ironhorse.mapper.FileStorageMapper;
import com.ironhorse.model.FileStorage;
import com.ironhorse.model.UserInfo;
import com.ironhorse.repository.FileStorageRepository;
import com.ironhorse.repository.UserInfoRepository;
import com.ironhorse.service.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileLocalStorageServiceImpl implements FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    private final UserInfoRepository userInfoRepository;
    private static final String CONTENT_PNG = "image/png";
    private static final String CONTENT_JPEG = "image/jpeg";
    private static final String PREFIX_FILENAME = "profile";

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, Long userId){
        try{
            this.validateFile(file);

            UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                    () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

            if(userInfo.getUserPicture() != null){
                this.deleteUserProfileFile(userId);
            }

            String fileName = this.generateFilename(file.getOriginalFilename());
            File uploadFile = new File(uploadDir + fileName);
            file.transferTo(uploadFile);

            FileStorage fileStorage = this.createFileStorage(fileName, uploadFile.getAbsolutePath(), file.getSize());
            userInfo.setUserPicture(fileStorage);

            this.fileStorageRepository.save(fileStorage);
        }catch (IOException e){
             e.getCause();
        }
    }

    @Override
    @Transactional
    public void deleteUserProfileFile(Long userId) {
        UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        if(userInfo.getUserPicture() == null){
            throw new IllegalArgumentException("Não há foto de perfil para ser deletada");
        }

        FileStorage fileStorage = this.fileStorageRepository.findById(userInfo.getUserPicture().getId()).orElseThrow(
                () -> new FileStorageNotFoundException("Arquivo não encontrado"));

        this.deleteFileStorage(fileStorage);

        userInfo.setUserPicture(null);
        this.fileStorageRepository.deleteById(fileStorage.getId());
        this.fileStorageRepository.flush();
    }

    @Override
    public FileStorageDto getUserProfile(Long userId) {
        UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        if(userInfo.getUserPicture() == null){
            throw new IllegalArgumentException("O usuário não possui foto");
        }

        FileStorage fileStorage = this.fileStorageRepository.findById(userInfo.getUserPicture().getId()).orElseThrow(
                () -> new FileStorageNotFoundException("Arquivo não encontrado"));

        return FileStorageMapper.toDto(fileStorage);
    }

    private String generateFilename(String originalFilename){
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

    private FileStorage createFileStorage(String fileName, String absolutePath, Long size){
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(fileName);
        fileStorage.setPath(absolutePath);
        fileStorage.setSize(size);

        return fileStorage;
    }

    private File deleteFileStorage(FileStorage fileStorage){
        File deleteFile = new File(fileStorage.getPath());
        if(deleteFile.exists()){
            boolean deleted = deleteFile.delete();
            if(!deleted){
                throw new IllegalArgumentException("Ocorreu um erro ao excluir a foto");
            }
        }

        return deleteFile;
    }
}
