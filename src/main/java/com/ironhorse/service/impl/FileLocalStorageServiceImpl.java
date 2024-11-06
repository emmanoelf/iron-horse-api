package com.ironhorse.service.impl;

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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileLocalStorageServiceImpl implements FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    private final UserInfoRepository userInfoRepository;
    private final AuthenticatedService authenticatedService;
    private final CarImagesRepository carImagesRepository;

    @Value("${spring.servlet.multipart.max-file-size}")
    private Long maxFileSize;
    private static final String CONTENT_PNG = "image/png";
    private static final String CONTENT_JPEG = "image/jpeg";
    private static final String PREFIX_FILENAME = "profile";
    private static final String PREFIX_CARIMAGEFILE = "car_image";
    private static final int MAX_FILES = 7;


    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    @Transactional
    public void uploadFile(MultipartFile file){
        try{
            this.validateFile(file);
            Long userId = this.authenticatedService.getCurrentUserId();

            UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                    () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

            if(userInfo.getUserPicture() != null){
                this.deleteUserProfileFile();
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

            for (MultipartFile file : files) {
                String fileName = this.generateCarImageFileName(file.getOriginalFilename());
                File uploadFile = new File(uploadDir + fileName);
                file.transferTo(uploadFile);

                CarImages carImagesStorage = this.createImageStorage(fileName, uploadFile.getAbsolutePath(), file.getSize());
                carImagesStorage.setCarInfo(car.getCarInfo()); // Associe a imagem ao carro
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

    @Transactional
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
            deleteImageFileFromStorage(carImage.getPath());
            carImagesRepository.delete(carImage);
        }
        car.getCarInfo().setCarImages(null);
        carImagesRepository.flush();
    }

    private void deleteImageFileFromStorage(String path) {
        String cleanedPath = path.trim();
        File file = new File(cleanedPath);

        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("Falha ao excluir o arquivo: " + path);
            }
        } else {
            throw new RuntimeException("Arquivo não encontrado: " + path);
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

    private String generateCarImageFileName(String originalFilename){
        long timestamp = System.currentTimeMillis();
        return String.format("%s_%d_%s", PREFIX_CARIMAGEFILE, timestamp, originalFilename);
    }

    private boolean isValidContentType(String contentType) {
        return CONTENT_PNG.equals(contentType) || CONTENT_JPEG.equals(contentType);
    }

    private void validateFile(MultipartFile file) throws FileUploadException {

        System.out.println(maxFileSize);
        if(file.getSize() > maxFileSize){
            throw new MaxUploadSizeExceededException(file.getSize());
        }

        if (file.isEmpty()) {
            throw new FileUploadException("Arquivo não encontrado");
        }

        if (!this.isValidContentType(file.getContentType())) {
            throw new FileUploadException("A imagem deve ser PNG ou JPEG");
        }
    }

    private void validateFiles(List<MultipartFile> files) throws FileUploadException {
        if (files.isEmpty()) {
            throw new FileUploadException("Arquivo não encontrado");
        }

        for(MultipartFile file : files){
            if(file.getSize() > maxFileSize){
                throw new MaxUploadSizeExceededException(file.getSize());
            }
        }

        for(MultipartFile file : files ){
            String contentType = file.getContentType();
            if(!this.isValidContentType(contentType)){
                throw new FileUploadException("A imagem deve ser de PNG ou JPEG");
            }
        }
    }

    private FileStorage createFileStorage(String fileName, String absolutePath, Long size){
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(fileName);
        fileStorage.setPath(absolutePath);
        fileStorage.setSize(size);

        return fileStorage;
    }

    private CarImages createImageStorage(String fileName, String absolutePath, Long size){
        CarImages carImages = new CarImages();
        carImages.setName(fileName);
        carImages.setPath(absolutePath);
        carImages.setSize(size);

        return carImages;
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