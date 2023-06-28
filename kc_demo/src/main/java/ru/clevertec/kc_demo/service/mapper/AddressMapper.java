package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.response.AddressReadDto;
import ru.clevertec.kc_demo.repository.entity.Address;

@Mapper(uses = CityMapper.class,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    Address createDtoToEntity(AddressCreateDto createDto);

    AddressReadDto entityToReadDto(Address address);

    void update(@MappingTarget Address address, AddressCreateDto createDto);

}
