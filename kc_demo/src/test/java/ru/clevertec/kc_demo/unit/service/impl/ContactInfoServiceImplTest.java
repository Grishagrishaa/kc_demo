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
import ru.clevertec.kc_demo.dto.request.ContactInfoCreateDto;
import ru.clevertec.kc_demo.dto.response.ContactInfoReadDto;
import ru.clevertec.kc_demo.repository.api.ContactInfoRepository;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;
import ru.clevertec.kc_demo.service.impl.ContactInfoServiceImpl;
import ru.clevertec.kc_demo.service.mapper.ContactInfoMapper;
import ru.clevertec.kc_demo.util.builder.impl.ContactInfoTestBuilder;

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
class ContactInfoServiceImplTest {

    @Mock
    private ContactInfoRepository repository;
    @Mock
    private ContactInfoMapper mapper = Mappers.getMapper(ContactInfoMapper.class);
    @InjectMocks
    private ContactInfoServiceImpl service;

    @Test
    void createShouldReturnReadDto() {
        ContactInfo contactInfo = ContactInfoTestBuilder.defaultValues().build();
        ContactInfoCreateDto createDto = ContactInfoTestBuilder.defaultValues().buildCreateDto();

        doReturn(contactInfo)
                .when(repository).save(any());
        doReturn(contactInfo)
                .when(mapper).createDtoToEntity(createDto);
        doReturn(ContactInfoTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(contactInfo);

        ContactInfoReadDto expected = ContactInfoTestBuilder.defaultValues().buildReadDto();
        ContactInfoReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnReadDto() {
        ContactInfo contactInfo = ContactInfoTestBuilder.defaultValues().build();

        doReturn(Optional.ofNullable(contactInfo))
                .when(repository).findById(contactInfo.getId());
        doReturn(ContactInfoTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(contactInfo);

        ContactInfoReadDto expected = ContactInfoTestBuilder.defaultValues().buildReadDto();
        ContactInfoReadDto actual = service.findById(contactInfo.getId());

        verify(repository).findById(contactInfo.getId());
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
        ContactInfo contactInfo = ContactInfoTestBuilder.defaultValues().build();
        ContactInfoReadDto readDto = ContactInfoTestBuilder.defaultValues().buildReadDto();

        doReturn(new PageImpl<>(List.of(contactInfo)))
                .when(repository).findAll(Pageable.unpaged());
        doReturn(ContactInfoTestBuilder.defaultValues().buildReadDto())
                .when(mapper).entityToReadDto(contactInfo);

        Page<ContactInfoReadDto> actual = service.findAll(Pageable.unpaged());
        Page<ContactInfoReadDto> expected = new PageImpl<>(List.of(readDto));

        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void updateByIdShouldCallRepository() {
        ContactInfoCreateDto updateDto = ContactInfoTestBuilder.defaultValues().buildCreateDto();
        ContactInfo contactInfo = ContactInfoTestBuilder.defaultValues().build();

        doReturn(Optional.of(contactInfo))
                .when(repository).findById(contactInfo.getId());
        doNothing()
                .when(mapper).update(contactInfo, updateDto);

        service.updateById(contactInfo.getId(), updateDto);

        verify(repository).findById(contactInfo.getId());
        verify(repository).save(contactInfo);
    }

    @Test
    void updateByIdShouldThrowEntityNotFoundException() {
        ContactInfoCreateDto updateDto = ContactInfoTestBuilder.defaultValues().buildCreateDto();

        doThrow(EntityNotFoundException.class)
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.updateById(any(), updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        ContactInfo contactInfo = ContactInfoTestBuilder.defaultValues().build();

        doReturn(Optional.of(contactInfo))
                .when(repository).findById(contactInfo.getId());

        service.deleteById(contactInfo.getId());

        verify(repository).findById(contactInfo.getId());
        verify(repository).delete(contactInfo);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        doReturn(Optional.empty())
                .when(repository).findById(any());

        assertThatThrownBy(() -> service.deleteById(any()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}