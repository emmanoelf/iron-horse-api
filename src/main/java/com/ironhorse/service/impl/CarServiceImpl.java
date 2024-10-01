package com.ironhorse.service.impl;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.CarMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.User;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Override
    public CarResponseDto save(CarDto carDto, Long id) {
        User user = this.userRepository.findById(id).orElseThrow((
                () -> new UserNotFound("Usuário não encontrado"))
        );

        Car car = CarMapper.toModel(carDto);
        car.setUser(user);
        this.carRepository.save(car);

        return CarMapper.toDto(car);
    }
}
