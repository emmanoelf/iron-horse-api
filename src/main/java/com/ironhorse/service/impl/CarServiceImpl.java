package com.ironhorse.service.impl;

import com.ironhorse.dto.*;
import com.ironhorse.exception.*;
import com.ironhorse.mapper.CarFeaturesMapper;
import com.ironhorse.mapper.CarInfoMapper;
import com.ironhorse.mapper.CarMapper;
import com.ironhorse.model.*;
import com.ironhorse.repository.*;
import com.ironhorse.repository.projection.CarResumeProjection;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.CarService;
import com.ironhorse.service.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;
    private final CarInfoRepository carInfoRepository;
    private final FileStorageService fileStorageService;
    private final UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public CarSaveResponseDto save(CarSaveDto carSaveDto) {

        Long userId = authenticatedService.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("Usuário não encontrado"));

        Car car = CarMapper.toModelWithNoCarInfos(carSaveDto);
        car.setUser(user);

        user.getCars().add(car);

        Car savedCar = carRepository.saveAndFlush(car);

        CarInfo carInfo = CarInfoMapper.toModel(carSaveDto.carInfoDto());
        carInfo.setCar(savedCar);

        CarFeatures carFeatures = CarFeaturesMapper.toModel(carSaveDto.carInfoDto().carFeaturesDto());
        carInfo.setCarFeatures(carFeatures);
        carFeatures.setCarInfo(carInfo);

        CarInfo savedCarInfo = carInfoRepository.saveAndFlush(carInfo);

        savedCar.setCarInfo(savedCarInfo);
        carRepository.save(savedCar);

        return CarMapper.toSaveResponseDto(savedCar);
    }



    @Override
    public CarResponseDto findById(Long id) {
        return this.carRepository.findById(id)
                .map(CarMapper::toDto)
                .orElseThrow(
                        () -> new CarNotFound("Carro não encontrado")
                );
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        Long userId = this.authenticatedService.getCurrentUserId();
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontradas"));

        Car car = this.carRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Carro não encontrado"));

        if (!car.getUser().getId().equals(userId)) {
            throw new ForbiddenAccessException("Você não possui privilégios para excluir carros alheios!");
        }

        if (car.getCarOverview() != null) {
            car.setCarOverview(null);
        }

        if (car.getCarInfo() != null) {
            if (car.getCarInfo().getCarImages() != null) {
                this.fileStorageService.deleteOnlyFromStorage(car);
            }
            car.setCarInfo(null);
        }

        car.getReviews().clear();
        car.getRentals().clear();

        this.carRepository.delete(car);

        return 1L;
    }

    @Transactional
    @Override
    public CarUpdateDto update(CarUpdateDto carUpdateDto, Long carId) {
        Long userId = this.authenticatedService.getCurrentUserId();

        UserInfo userInfo = this.userInfoRepository.findByUserId(userId).orElseThrow(
                () -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

        Car car = userInfo.getUser().getCars().stream()
                .filter(carro -> carro.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não há carros para fazer update"));

        if (!car.getUser().getId().equals(userId)) {
            throw new ForbiddenAccessException("Você não pode acessar este recurso");
        }

        car.setBrand(carUpdateDto.brand());
        car.setModel(carUpdateDto.model());
        car.setManufactureYear(carUpdateDto.manufactureYear());

        CarInfo carInfo = car.getCarInfo();
        if (carInfo == null) {
            carInfo = new CarInfo();
            car.setCarInfo(carInfo);
        }

        carInfo.setChassi(carUpdateDto.carInfoUpdateDto().chassi());
        carInfo.setCylinderDisplacement(carUpdateDto.carInfoUpdateDto().cylinderDisplacement());
        carInfo.setDirectionType(carUpdateDto.carInfoUpdateDto().directionType());
        carInfo.setEngineNumber(carUpdateDto.carInfoUpdateDto().engineNumber());
        carInfo.setFuelType(carUpdateDto.carInfoUpdateDto().fuelType());
        carInfo.setInsurance(carUpdateDto.carInfoUpdateDto().insurance());
        carInfo.setInsuranceName(carUpdateDto.carInfoUpdateDto().insuranceName());
        carInfo.setLicensePlate(carUpdateDto.carInfoUpdateDto().licensePlate());
        carInfo.setMileage(carUpdateDto.carInfoUpdateDto().mileage());
        carInfo.setRenavam(carUpdateDto.carInfoUpdateDto().renavam());
        carInfo.setTransmission(carUpdateDto.carInfoUpdateDto().transmission());
        carInfo.setColor(carUpdateDto.carInfoUpdateDto().color());
        carInfo.setNumDoors(carUpdateDto.carInfoUpdateDto().numDoors());
        carInfo.setNumSeats(carUpdateDto.carInfoUpdateDto().numSeats());
        carInfo.setHeadlightBulb(carUpdateDto.carInfoUpdateDto().headlightBulb());
        carInfo.setTrunkCapacity(carUpdateDto.carInfoUpdateDto().trunkCapacity());
        CarFeatures carFeatures = carInfo.getCarFeatures();
        if (carFeatures == null) {
            carFeatures = new CarFeatures();
            carInfo.setCarFeatures(carFeatures);
        }

        CarFeaturesUpdateDto featuresDto = carUpdateDto.carInfoUpdateDto().carFeaturesUpdateDto();

        carFeatures.setInsulfilm(featuresDto.insulfilm());
        carFeatures.setTagPike(featuresDto.tagPike());
        carFeatures.setAntiTheftSecret(featuresDto.antiTheftSecret());
        carFeatures.setMultimedia(featuresDto.multimedia());
        carFeatures.setAirConditioner(featuresDto.airConditioner());
        carFeatures.setElectricWindowsAndLocks(featuresDto.electricWindowsAndLocks());
        carFeatures.setTriangle(featuresDto.triangle());
        carFeatures.setJack(featuresDto.jack());
        carFeatures.setWheelWrench(featuresDto.wheelWrench());
        carFeatures.setSpareTire(featuresDto.spareTire());
        carFeatures.setFireExtinguisher(featuresDto.fireExtinguisher());
        carFeatures.setAlarm(featuresDto.alarm());
        carFeatures.setSmokersAccepted(featuresDto.smokersAccepted());
        carFeatures.setTagActivated(featuresDto.tagActivated());
        carFeatures.setFinesBelongToTheOffender(featuresDto.isFinesBelongToTheOffender());
        carFeatures.setDocsUptoDate(featuresDto.isDocsUptoDate());
        carFeatures.setVeicleModified(featuresDto.isVeicleModified());
        carFeatures.setTrueInformation(featuresDto.isTrueInformation());

        this.carRepository.save(car);

        return CarMapper.carUpdateDto(car);
    }


    @Override
    public Page<CarResumeProjection> findAllCarsByCity(String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.carRepository.findCarResumesByCity(city, pageable);
    }
}
