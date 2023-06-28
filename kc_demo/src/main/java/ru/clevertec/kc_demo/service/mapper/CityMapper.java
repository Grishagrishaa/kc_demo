package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.response.CityReadDto;
import ru.clevertec.kc_demo.repository.api.CityRepository;
import ru.clevertec.kc_demo.repository.entity.City;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CityMapper {


    @Autowired
    protected CityRepository repository;

    public City createDtoToEntity(CityCreateDto createDto){
        return repository.findByName(createDto.getName())
                .orElseGet(() -> repository.save(City.builder()
                            .setName(createDto.getName())
                            .setPopulation(createDto.getPopulation())
                            .build()));
    }

    public abstract CityReadDto entityToReadDto(City city);

    public abstract void update(@MappingTarget City city, CityCreateDto createDto);

}
