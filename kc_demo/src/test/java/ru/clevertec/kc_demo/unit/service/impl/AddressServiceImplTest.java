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
import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.response.AddressReadDto;
import ru.clevertec.kc_demo.repository.api.AddressRepository;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.service.impl.AddressServiceImpl;
import ru.clevertec.kc_demo.service.mapper.AddressMapper;
import ru.clevertec.kc_demo.util.builder.impl.AddressTestBuilder;

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
class AddressServiceImplTest {

    @Mock
    private AddressRepository repository;
    @Mock
    private AddressMapper mapper = Mappers.getMapper(AddressMapper.class);
    @InjectMocks
    private AddressServiceImpl service;

    @Test
    void createShouldReturnReadDto() {
        Address address = AddressTestBuilder.defaultValues().build();
        AddressCreateDto createDto = AddressTestBuilder.defaultValues().buildCreateDto();

        doReturn(address)
                .when(repository).save(any());
        doReturn(address)
                .when(mapper).createDtoToEntity(createDto);
        doReturn(AddressTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(address);

        AddressReadDto expected = AddressTestBuilder.defaultValues().buildReadDto();
        AddressReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnReadDto() {
        Address address = AddressTestBuilder.defaultValues().build();

        doReturn(Optional.ofNullable(address))
                .when(repository).findById(address.getId());
        doReturn(AddressTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(address);

        AddressReadDto expected = AddressTestBuilder.defaultValues().buildReadDto();
        AddressReadDto actual = service.findById(address.getId());

        verify(repository).findById(address.getId());
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
        Address address = AddressTestBuilder.defaultValues().build();
        AddressReadDto readDto = AddressTestBuilder.defaultValues().buildReadDto();

        doReturn(new PageImpl<>(List.of(address)))
                .when(repository).findAll(Pageable.unpaged());
        doReturn(AddressTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(address);

        Page<AddressReadDto> actual = service.findAll(Pageable.unpaged());
        Page<AddressReadDto> expected = new PageImpl<>(List.of(readDto));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void updateByIdShouldCallRepository() {
        AddressCreateDto updateDto = AddressTestBuilder.defaultValues().buildCreateDto();
        Address address = AddressTestBuilder.defaultValues().build();

        doReturn(Optional.of(address))
                .when(repository).findById(address.getId());
        doNothing()
                .when(mapper).update(address, updateDto);

        service.updateById(address.getId(), updateDto);

        verify(repository).findById(address.getId());
        verify(repository).save(address);
    }

    @Test
    void updateByIdShouldThrowEntityNotFoundException() {
        AddressCreateDto updateDto = AddressTestBuilder.defaultValues().buildCreateDto();

        doThrow(EntityNotFoundException.class)
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.updateById(any(), updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        Address address = AddressTestBuilder.defaultValues().build();

        doReturn(Optional.of(address))
                .when(repository).findById(address.getId());

        service.deleteById(address.getId());

        verify(repository).findById(address.getId());
        verify(repository).delete(address);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.deleteById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}