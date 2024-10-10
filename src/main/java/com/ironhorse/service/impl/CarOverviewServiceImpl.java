package com.ironhorse.service.impl;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.mapper.CarOverviewMapper;
import com.ironhorse.model.CarOverview;
import com.ironhorse.repository.CarOverviewRepository;
import com.ironhorse.service.CarOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarOverviewServiceImpl implements CarOverviewService {
    private final CarOverviewRepository carOverviewRepository;
    private final CarOverviewMapper carOverviewMapper;

    // Criar CarOverview
    public CarOverviewDto createCarOverview(CarOverviewDto carOverviewDto, CarDto carDto) {
        CarOverview carOverview = CarOverviewMapper.toModel(carOverviewDto);
        CarOverview savedCarOverview = carOverviewRepository.save(carOverview);
        return CarOverviewMapper.toDto(savedCarOverview);
    }

    @Override
    public CarOverviewDto createCarOverview(CarOverviewDto carOverviewDto) {
        return null;
    }

    // Buscar todos os CarOverview
    public List<CarOverviewDto> getAllCarOverviews() {
        List<CarOverview> carOverviews = carOverviewRepository.findAll();
        return carOverviews.stream()
                .map(CarOverviewMapper::toDto)
                .toList();
    }

    // Buscar CarOverview por ID
    public Optional<CarOverviewDto> getCarOverviewById(Long id) {
        return carOverviewRepository.findById(id)
                .map(CarOverviewMapper::toDto);
    }

    // Atualizar CarOverview
    public CarOverviewDto updateCarOverview(Long id, CarOverviewDto carOverviewDto) {
        CarOverview carOverview = CarOverviewMapper.toModel(carOverviewDto);
        carOverview.setId(id); // Define o ID para garantir a atualização correta
        CarOverview updatedCarOverview = carOverviewRepository.save(carOverview);
        return CarOverviewMapper.toDto(updatedCarOverview);
    }

    // Deletar CarOverview
    public void deleteCarOverview(Long id) {
        carOverviewRepository.deleteById(id);
    }
}
