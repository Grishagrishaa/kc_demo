package ru.clevertec.kc_demo.unit.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.kc_demo.dto.request.SkillCreateDto;
import ru.clevertec.kc_demo.dto.response.SkillReadDto;
import ru.clevertec.kc_demo.repository.api.SkillRepository;
import ru.clevertec.kc_demo.repository.entity.Skill;
import ru.clevertec.kc_demo.service.impl.SkillServiceImpl;
import ru.clevertec.kc_demo.service.mapper.SkillMapper;
import ru.clevertec.kc_demo.util.builder.impl.SkillTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {

    @Mock
    private SkillRepository repository;
    @Mock
    private SkillMapper mapper = Mappers.getMapper(SkillMapper.class);
    @InjectMocks
    private SkillServiceImpl service;

    @Test
    void createShouldReturnReadDto() {
        Skill skill = SkillTestBuilder.defaultValues().build();
        SkillCreateDto createDto = SkillTestBuilder.defaultValues().buildCreateDto();

        doReturn(skill)
                .when(repository).save(any());
        doReturn(skill)
                .when(mapper).createDtoToEntity(createDto);
        doReturn(SkillTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(skill);

        SkillReadDto expected = SkillTestBuilder.defaultValues().buildReadDto();
        SkillReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnReadDto() {
        Skill skill = SkillTestBuilder.defaultValues().build();

        doReturn(Optional.ofNullable(skill))
                .when(repository).findById(skill.getId());
        doReturn(SkillTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(skill);

        SkillReadDto expected = SkillTestBuilder.defaultValues().buildReadDto();
        SkillReadDto actual = service.findById(skill.getId());

        verify(repository).findById(skill.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect() {

        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.findById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllShouldReturnCorrectPage() {
        Skill skill = SkillTestBuilder.defaultValues().build();
        SkillReadDto readDto = SkillTestBuilder.defaultValues().buildReadDto();

        doReturn(new PageImpl<>(List.of(skill)))
                .when(repository).findAll(Pageable.unpaged());
        doReturn(SkillTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(skill);

        Page<SkillReadDto> actual = service.findAll(Pageable.unpaged());
        Page<SkillReadDto> expected = new PageImpl<>(List.of(readDto));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void updateByIdShouldCallRepository() {
        SkillCreateDto updateDto = SkillTestBuilder.defaultValues().buildCreateDto();
        Skill skill = SkillTestBuilder.defaultValues().build();

        doReturn(Optional.of(skill))
                .when(repository).findById(skill.getId());
        doNothing()
                .when(mapper).update(skill, updateDto);

        service.updateById(skill.getId(), updateDto);

        verify(repository).findById(skill.getId());
        verify(repository).save(skill);
    }

    @Test
    void updateByIdShouldThrowEntityNotFoundException() {
        SkillCreateDto updateDto = SkillTestBuilder.defaultValues().buildCreateDto();

        doThrow(EntityNotFoundException.class)
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.updateById(any(), updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        Skill skill = SkillTestBuilder.defaultValues().build();

        doReturn(Optional.of(skill))
                .when(repository).findById(skill.getId());

        service.deleteById(skill.getId());

        verify(repository).findById(skill.getId());
        verify(repository).delete(skill);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.deleteById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}