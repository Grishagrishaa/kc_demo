package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;
import ru.clevertec.kc_demo.repository.entity.Department;

@Mapper(uses = CityMapper.class,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

    Department createDtoToEntity(DepartmentCreateDto createDto);

    DepartmentReadDto entityToReadDto(Department department);

    void update(@MappingTarget Department Department, DepartmentCreateDto createDto);

}
