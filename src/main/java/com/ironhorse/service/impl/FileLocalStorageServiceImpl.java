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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileLocalStorageServiceImpl implements FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    private final UserInfoRepository userInfoRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, Long userId){
        try{
            if(file.isEmpty()){
                throw new FileUploadException("Arquivo não encontrado");
            }

            if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
                throw new FileUploadException("A imagem deve ser PNG ou JPEG");
            }

            UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                    () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

            if(userInfo.getUserPicture() != null){
                this.deleteUserProfileFile(userId);
            }

            String originalFilename = file.getOriginalFilename();
            long timestamp = System.currentTimeMillis();
            String fileName = "profile_" + timestamp + "_" + originalFilename;

            File uploadFile = new File(uploadDir + fileName);

            file.transferTo(uploadFile);

            FileStorage fileStorage = new FileStorage();
            fileStorage.setName(fileName);
            fileStorage.setPath(uploadFile.getAbsolutePath());
            fileStorage.setSize(file.getSize());
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

        Optional<FileStorage> fileStorage = this.fileStorageRepository.findById(userInfo.getUserPicture().getId());
        File deleteFile = new File(fileStorage.get().getPath());
        if(deleteFile.exists()){
            boolean deleted = deleteFile.delete();
            if(!deleted){
                throw new IllegalArgumentException("Ocorreu um erro ao excluir a foto");
            }
        }

        userInfo.setUserPicture(null);
        this.fileStorageRepository.deleteById(fileStorage.get().getId());
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
}
