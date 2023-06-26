package ru.clevertec.kc_demo.unit.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;
import ru.clevertec.kc_demo.repository.api.EmployeeRepository;
import ru.clevertec.kc_demo.repository.entity.Employee;
import ru.clevertec.kc_demo.service.impl.EmployeeServiceImpl;
import ru.clevertec.kc_demo.service.mapper.EmployeeMapper;
import ru.clevertec.kc_demo.util.builder.impl.EmployeeTestBuilder;

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
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;
    @Spy
    private EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
    @InjectMocks
    private EmployeeServiceImpl service;

    @Test
    void createShouldReturnReadDto() {
        Employee employee = EmployeeTestBuilder.defaultValues().build();
        EmployeeCreateDto createDto = EmployeeTestBuilder.toCreateDto(employee);

        doReturn(employee)
                .when(repository).save(any());
        doReturn(employee)
                .when(mapper).createDtoToEntity(createDto);
        doReturn(EmployeeTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(employee);

        EmployeeReadDto expected = EmployeeTestBuilder.defaultValues().buildReadDto();
        EmployeeReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnReadDto() {
        Employee employee = EmployeeTestBuilder.defaultValues().build();

        doReturn(Optional.ofNullable(employee))
                .when(repository).findById(employee.getId());
        doReturn(EmployeeTestBuilder.toReadDto(employee))
                .when(mapper).entityToReadDto(employee);

        EmployeeReadDto expected = EmployeeTestBuilder.toReadDto(employee);
        EmployeeReadDto actual = service.findById(employee.getId());

        verify(repository).findById(employee.getId());
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
    void findAllPageableShouldReturnCorrectPage() {
        Employee employee = EmployeeTestBuilder.defaultValues().build();

        doReturn(new PageImpl<>(List.of(employee)))
                .when(repository).findAll(any(Pageable.class));
        doReturn(EmployeeTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(employee);

        Page<EmployeeReadDto> actual = service.findAllPageable(Pageable.unpaged());
        Page<EmployeeReadDto> expected = new PageImpl<>(List.of(EmployeeTestBuilder.toReadDto(employee)));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void findAllByEmployeeFilterShouldReturnCorrectPage() {
        Employee employee = EmployeeTestBuilder.defaultValues().build();
        EmployeeReadDto readDto = EmployeeTestBuilder.toReadDto(employee);

        doReturn(new PageImpl<>(List.of(employee)))
                .when(repository).findAll(any(Specification.class), any(Pageable.class));
        doReturn(EmployeeTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(employee);

        Page<EmployeeReadDto> actual = service.findAllByEmployeeFilter(EmployeeFilter.defaultValues(), Pageable.unpaged());
        Page<EmployeeReadDto> expected = new PageImpl<>(List.of(readDto));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void updateByIdShouldCallRepository() {
        EmployeeCreateDto updateDto = EmployeeTestBuilder.defaultValues().buildCreateDto();
        Employee employee = EmployeeTestBuilder.defaultValues().build();

        doReturn(Optional.of(employee))
                .when(repository).findById(employee.getId());
        doNothing()
                .when(mapper).update(employee, updateDto);

        service.updateById(employee.getId(), updateDto);

        verify(repository).findById(employee.getId());
        verify(repository).save(employee);
    }

    @Test
    void updateByIdShouldThrowEntityNotFoundException() {
        EmployeeCreateDto updateDto = EmployeeTestBuilder.defaultValues().buildCreateDto();

        doThrow(EntityNotFoundException.class)
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.updateById(any(), updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        Employee employee = EmployeeTestBuilder.defaultValues().build();

        doReturn(Optional.of(employee))
                .when(repository).findById(employee.getId());

        service.deleteById(employee.getId());

        verify(repository).findById(employee.getId());
        verify(repository).delete(employee);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.deleteById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}