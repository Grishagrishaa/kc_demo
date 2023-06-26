package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.kc_demo.dto.request.SkillsCreateDto;
import ru.clevertec.kc_demo.dto.response.SkillsReadDto;
import ru.clevertec.kc_demo.repository.entity.Skill;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkillsMapper {

    Skill createDtoToEntity(SkillsCreateDto createDto);

    SkillsReadDto entityToReadDto(Skill skill);

    void update(@MappingTarget Skill Skill, SkillsCreateDto createDto);

}
