package com.ironhorse.service.impl;

import com.ironhorse.dto.CarInfoConsentsDto;
import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.exception.CarNotFound;
import com.ironhorse.exception.UserInfoNotFoundException;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.CarFeaturesMapper;
import com.ironhorse.mapper.CarInfoMapper;
import com.ironhorse.model.*;
import com.ironhorse.repository.*;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.CarInfoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class carInfoServiceImpl implements CarInfoService {

 private final CarInfoRepository carInfoRepository;
 private final CarRepository carRepository;
 private final CarFeaturesRepository carFeaturesRepository;
 private final AuthenticatedService authenticatedService;
 private final UserRepository userRepository;

 @Override
 public CarInfoDto findByCarId(Long carId) {
  return this.carInfoRepository.findByCarId(carId)
          .map(CarInfoMapper::toDto)
          .orElseThrow(
                  () -> new CarNotFound("Informacao do carro não encontrada")
          );
 }

 @Override
 @Transactional
 public Long deleteByCarId(Long carId) {
  Long affectedRow = this.carInfoRepository.deleteByCarId(carId);

  if (affectedRow == 0) {
   throw new CarNotFound("Carro não encontrado");
  }

  return affectedRow;
 }

 @Transactional
 @Override
 public CarInfoDto save(CarInfoDto carInfoDto) {

  Long userId = authenticatedService.getCurrentUserId();
  User user = userRepository.findById(userId)
          .orElseThrow(() -> new UserInfoNotFoundException("Informações do usuário não encontrada"));

 Car car = new Car();
 car.setUser(user);

  CarInfo carInfo = CarInfoMapper.toModel(carInfoDto);
  carInfo.setCar(car);
  this.carInfoRepository.save(carInfo);

  CarFeatures carFeatures = carInfo.getCarFeatures();
  if (carFeatures != null) {
   this.carFeaturesRepository.save(carFeatures);
  }

  user.getCars().add(car);
  userRepository.save(user);
  return CarInfoMapper.toDto(carInfo);
 }

 @Transactional
 @Override
 public CarInfoConsentsDto saveConsents(CarInfoConsentsDto carInfoConsentsDto, Long id) {

  Car car = this.carRepository.findById(id).orElseThrow(
          () -> new UserNotFound("Informações do veículo não encontradas"));

  CarFeatures carFeatures = this.carFeaturesRepository.findByCarInfoId(car.getCarInfo().getId()).orElseThrow(
          () -> new UserNotFound("Características do veículo não encontradas"));

  carFeatures.setSmokersAccepted(carInfoConsentsDto.smokersAccepted());
  carFeatures.setTagActivated(carInfoConsentsDto.tagActivated());
  carFeatures.setFinesBelongToTheOffender(carInfoConsentsDto.finesBelongToTheOffender());
  carFeatures.setVeicleModified(carInfoConsentsDto.veicleModified());
  carFeatures.setTrueInformation(carInfoConsentsDto.trueInformation());
  carFeatures.setDocsUptoDate(carInfoConsentsDto.docsUptoDate());

  this.carFeaturesRepository.save(carFeatures);

  return CarFeaturesMapper.toPartialDto(carFeatures);
 }

 @Override
 @Transactional
 public CarInfoDto update(CarInfoDto carInfoDto, Long carId) {
  CarInfo existingCarInfo = carInfoRepository.findByCarId(carId)
          .orElseThrow(() -> new CarNotFound("Informações do carro não encontradas"));

  existingCarInfo.setLicensePlate(carInfoDto.licensePlate());
  existingCarInfo.setTransmission(carInfoDto.transmission());
  existingCarInfo.setDirectionType(carInfoDto.directionType());
  existingCarInfo.setChassi(carInfoDto.chassi());
  existingCarInfo.setEngineNumber(carInfoDto.engineNumber());
  existingCarInfo.setCylinderDisplacement(carInfoDto.cylinderDisplacement());
  existingCarInfo.setMileage(carInfoDto.mileage());
  existingCarInfo.setFuelType(carInfoDto.fuelType());
  existingCarInfo.setRenavam(carInfoDto.renavam());

  carInfoRepository.save(existingCarInfo);

  return CarInfoMapper.toDto(existingCarInfo);
 }

}