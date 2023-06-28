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
import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.response.CityReadDto;
import ru.clevertec.kc_demo.repository.api.CityRepository;
import ru.clevertec.kc_demo.repository.entity.City;
import ru.clevertec.kc_demo.service.impl.CityServiceImpl;
import ru.clevertec.kc_demo.service.mapper.CityMapper;
import ru.clevertec.kc_demo.util.builder.impl.CityTestBuilder;

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
class CityServiceImplTest {

    @Mock
    private CityRepository repository;
    @Mock
    private CityMapper mapper = Mappers.getMapper(CityMapper.class);
    @InjectMocks
    private CityServiceImpl service;

    @Test
    void createShouldReturnReadDto() {
        City city = CityTestBuilder.defaultValues().build();
        CityCreateDto createDto = CityTestBuilder.defaultValues().buildCreateDto();

        doReturn(city)
                .when(repository).save(any());
        doReturn(city)
                .when(mapper).createDtoToEntity(createDto);
        doReturn(CityTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(city);

        CityReadDto expected = CityTestBuilder.defaultValues().buildReadDto();
        CityReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnReadDto() {
        City city = CityTestBuilder.defaultValues().build();

        doReturn(Optional.ofNullable(city))
                .when(repository).findById(city.getId());
        doReturn(CityTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(city);

        CityReadDto expected = CityTestBuilder.defaultValues().buildReadDto();
        CityReadDto actual = service.findById(city.getId());

        verify(repository).findById(city.getId());
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
        City city = CityTestBuilder.defaultValues().build();
        CityReadDto readDto = CityTestBuilder.defaultValues().buildReadDto();

        doReturn(new PageImpl<>(List.of(city)))
                .when(repository).findAll(Pageable.unpaged());
        doReturn(CityTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(city);

        Page<CityReadDto> actual = service.findAll(Pageable.unpaged());
        Page<CityReadDto> expected = new PageImpl<>(List.of(readDto));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void updateByIdShouldCallRepository() {
        CityCreateDto updateDto = CityTestBuilder.defaultValues().buildCreateDto();
        City city = CityTestBuilder.defaultValues().build();

        doReturn(Optional.of(city))
                .when(repository).findById(city.getId());
        doNothing()
                .when(mapper).update(city, updateDto);

        service.updateById(city.getId(), updateDto);

        verify(repository).findById(city.getId());
        verify(repository).save(city);
    }

    @Test
    void updateByIdShouldThrowEntityNotFoundException() {
        CityCreateDto updateDto = CityTestBuilder.defaultValues().buildCreateDto();

        doThrow(EntityNotFoundException.class)
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.updateById(any(), updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        City city = CityTestBuilder.defaultValues().build();

        doReturn(Optional.of(city))
                .when(repository).findById(city.getId());

        service.deleteById(city.getId());

        verify(repository).findById(city.getId());
        verify(repository).delete(city);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.deleteById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}