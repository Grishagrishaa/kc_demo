package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.kc_demo.dto.request.ContactInfoCreateDto;
import ru.clevertec.kc_demo.dto.response.ContactInfoReadDto;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;

@Mapper(uses = CityMapper.class,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactInfoMapper {

    ContactInfo createDtoToEntity(ContactInfoCreateDto createDto);

    ContactInfoReadDto entityToReadDto(ContactInfo contactInfo);

    void update(@MappingTarget ContactInfo ContactInfo, ContactInfoCreateDto createDto);

}
