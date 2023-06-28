package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.kc_demo.dto.request.SkillCreateDto;
import ru.clevertec.kc_demo.dto.response.SkillReadDto;
import ru.clevertec.kc_demo.repository.entity.Skill;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkillMapper {

    Skill createDtoToEntity(SkillCreateDto createDto);

    SkillReadDto entityToReadDto(Skill skill);

    void update(@MappingTarget Skill Skill, SkillCreateDto createDto);

}
