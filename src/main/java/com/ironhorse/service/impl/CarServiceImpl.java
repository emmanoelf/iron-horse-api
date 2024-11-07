package com.ironhorse.service.impl;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.exception.CarNotFound;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.CarFeaturesMapper;
import com.ironhorse.mapper.CarInfoMapper;
import com.ironhorse.mapper.CarMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.CarFeatures;
import com.ironhorse.model.CarInfo;
import com.ironhorse.model.User;
import com.ironhorse.repository.CarFeaturesRepository;
import com.ironhorse.repository.CarInfoRepository;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.repository.projection.CarResumeProjection;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;
    private final CarInfoRepository carInfoRepository;
    private final CarFeaturesRepository carFeaturesRepository;

    @Override
    @Transactional
    public CarSaveDto save(CarSaveDto carSaveDto) {

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

        return CarMapper.toSaveDto(savedCar);
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
        Long affectedRow = this.carRepository.deleteCarById(id);

        if(affectedRow == 0){
            throw new CarNotFound("Carro não encontrado");
        }

        return affectedRow;
    }

    @Override
    @Transactional
    public CarResponseDto update(CarDto carDto, Long carId, Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if(!user.isPresent()){
            throw new UserNotFound("Usuário não encontrado");
        }

        Car car = this.carRepository.findById(carId).orElseThrow(() -> new CarNotFound("Carro não encontrado"));

        if(!car.getUser().getId().equals(userId)){
            throw new ForbiddenAccessException("Você não pode acessar este recurso");
        }

        car = CarMapper.toModel(carDto);
        car.setBrand(carDto.brand());
        car.setModel(carDto.model());
        car.setManufactureYear(carDto.manufactureYear());
        car.setUser(user.get());

        this.carRepository.save(car);
        return CarMapper.toDto(car);
    }

    @Override
    public List<CarResumeProjection> findAllCarsByCity(String city) {
        return this.carRepository.findCarResumesByCity(city);
    }
}
