package com.ironhorse.service.impl;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.exception.CarNotFound;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.CarInfoMapper;
import com.ironhorse.mapper.CarMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.CarInfo;
import com.ironhorse.repository.CarInfoRepository;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.service.CarInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class carInfoServiceImpl implements CarInfoService {

 private final CarInfoRepository carInfoRepository;
 private final CarRepository carRepository;

 @Override
 public CarInfoDto findCarById(Long carId) {
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

 @Override
 @Transactional
 public CarInfoDto save(CarInfoDto carDto, Long id) {
  Car car = this.carRepository.findById(id).orElseThrow((
          () -> new UserNotFound("informacoes do veiculo nao encontradas"))
  );

  CarInfo carInfo = CarInfoMapper.toModel(carDto);
  carInfo.setCar(car);
  this.carInfoRepository.save(carInfo);

  return CarInfoMapper.toDto(carInfo);
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
  existingCarInfo.setEngineHorsePower(carInfoDto.engineHorsePower());
  existingCarInfo.setMileage(carInfoDto.mileage());
  existingCarInfo.setFuelType(carInfoDto.fuelType());
  existingCarInfo.setRenavam(carInfoDto.renavam());
  existingCarInfo.setInsuranceName(carInfoDto.insuranceName());
  existingCarInfo.setInsurance(carInfoDto.insurance());
  existingCarInfo.setInsulfilm(carInfoDto.insulfilm());
  existingCarInfo.setTagPike(carInfoDto.tagPike());
  existingCarInfo.setAntiTheftSecret(carInfoDto.antiTheftSecret());
  existingCarInfo.setMultimedia(carInfoDto.multimedia());
  existingCarInfo.setAirConditioner(carInfoDto.airConditioner());
  existingCarInfo.setElectricWindowsAndLocks(carInfoDto.electricWindowsAndLocks());
  existingCarInfo.setTriangle(carInfoDto.triangle());
  existingCarInfo.setMonkey(carInfoDto.monkey());
  existingCarInfo.setWheelWrench(carInfoDto.wheelWrench());
  existingCarInfo.setSpareTire(carInfoDto.spareTire());
  existingCarInfo.setFireExtinguisher(carInfoDto.fireExtinguisher());
  existingCarInfo.setAlarm(carInfoDto.alarm());
  existingCarInfo.setSmokersAccepted(carInfoDto.smokersAccepted());
  existingCarInfo.setTagActivated(carInfoDto.tagActivated());
  existingCarInfo.setIsFinesBelongToTheOffender(carInfoDto.isFinesBelongToTheOffender());
  existingCarInfo.setIsTermsUser(carInfoDto.isTermsUser());

  carInfoRepository.save(existingCarInfo);

  return CarInfoMapper.toDto(existingCarInfo);
 }

}