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
import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;
import ru.clevertec.kc_demo.repository.api.DepartmentRepository;
import ru.clevertec.kc_demo.repository.entity.Department;
import ru.clevertec.kc_demo.service.impl.DepartmentServiceImpl;
import ru.clevertec.kc_demo.service.mapper.DepartmentMapper;
import ru.clevertec.kc_demo.util.builder.impl.DepartmentTestBuilder;

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
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository repository;
    @Mock
    private DepartmentMapper mapper = Mappers.getMapper(DepartmentMapper.class);
    @InjectMocks
    private DepartmentServiceImpl service;

    @Test
    void createShouldReturnReadDto() {
        Department department = DepartmentTestBuilder.defaultValues().build();
        DepartmentCreateDto createDto = DepartmentTestBuilder.defaultValues().buildCreateDto();

        doReturn(department)
                .when(repository).save(any());
        doReturn(department)
                .when(mapper).createDtoToEntity(createDto);
        doReturn(DepartmentTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(department);

        DepartmentReadDto expected = DepartmentTestBuilder.defaultValues().buildReadDto();
        DepartmentReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnReadDto() {
        Department department = DepartmentTestBuilder.defaultValues().build();

        doReturn(Optional.ofNullable(department))
                .when(repository).findById(department.getId());
        doReturn(DepartmentTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(department);

        DepartmentReadDto expected = DepartmentTestBuilder.defaultValues().buildReadDto();
        DepartmentReadDto actual = service.findById(department.getId());

        verify(repository).findById(department.getId());
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
        Department department = DepartmentTestBuilder.defaultValues().build();
        DepartmentReadDto readDto = DepartmentTestBuilder.defaultValues().buildReadDto();

        doReturn(new PageImpl<>(List.of(department)))
                .when(repository).findAll(Pageable.unpaged());
        doReturn(DepartmentTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(department);

        Page<DepartmentReadDto> actual = service.findAll(Pageable.unpaged());
        Page<DepartmentReadDto> expected = new PageImpl<>(List.of(readDto));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void updateByIdShouldCallRepository() {
        DepartmentCreateDto updateDto = DepartmentTestBuilder.defaultValues().buildCreateDto();
        Department department = DepartmentTestBuilder.defaultValues().build();

        doReturn(Optional.of(department))
                .when(repository).findById(department.getId());
        doNothing()
                .when(mapper).update(department, updateDto);

        service.updateById(department.getId(), updateDto);

        verify(repository).findById(department.getId());
        verify(repository).save(department);
    }

    @Test
    void updateByIdShouldThrowEntityNotFoundException() {
        DepartmentCreateDto updateDto = DepartmentTestBuilder.defaultValues().buildCreateDto();

        doThrow(EntityNotFoundException.class)
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.updateById(any(), updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        Department department = DepartmentTestBuilder.defaultValues().build();

        doReturn(Optional.of(department))
                .when(repository).findById(department.getId());

        service.deleteById(department.getId());

        verify(repository).findById(department.getId());
        verify(repository).delete(department);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.deleteById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}