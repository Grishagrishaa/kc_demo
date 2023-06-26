package ru.clevertec.kc_demo.integration.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;
import ru.clevertec.kc_demo.integration.BaseIntegrationTest;
import ru.clevertec.kc_demo.service.EmployeeService;
import ru.clevertec.kc_demo.util.builder.impl.EmployeeTestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EmployeeServiceImplTest extends BaseIntegrationTest {//todo n+1 docker kc

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private EmployeeService service;

    @Test
    void createUserShouldReturnReadDtoWithUuid() {
        EmployeeReadDto employeeReadDto = EmployeeTestBuilder.defaultValues().buildReadDto();

        EmployeeReadDto actual = service.create(EmployeeTestBuilder.defaultValues().buildCreateDto());

        assertThat(actual).isNotNull();
    }

    @Test
    void findByIdShouldReturnReadDto() {
        EmployeeReadDto actual = service.findById(EMPLOYEE_UUID);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(EMPLOYEE_UUID);
    }

    @Test
    void findByIdShouldThrowExceptionIfIdInvalid() {
        assertThatThrownBy(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllPageableShouldReturnCorrectPage() {
        Page<EmployeeReadDto> actual = service
                .findAllPageable(Pageable.unpaged());

        assertThat(actual).hasSize(COUNT_OF_EMPLOYEES);
    }

    @Test
    void findAllByEmployeeFilterShouldReturnCorrectPage() {
        Page<EmployeeReadDto> actual = service
                .findAllByEmployeeFilter(EmployeeFilter.defaultValues(), Pageable.unpaged());

        assertThat(actual).hasSize(COUNT_OF_EMPLOYEES);
    }

    @Test
    void updateByUuidShouldReturnUpdatedReadDto() {

        EmployeeCreateDto toUpdate = EmployeeTestBuilder.defaultValues().buildCreateDto();
        EmployeeReadDto notUpdated = service.findById(EMPLOYEE_UUID);

        EmployeeReadDto updated = service.updateById(EMPLOYEE_UUID, toUpdate);

        assertAll(
                () -> assertThat(notUpdated).isNotEqualTo(toUpdate),

                () -> assertThat(updated.getName()).isEqualTo(toUpdate.getName()),
                () -> assertThat(updated.getLastname()).isEqualTo(toUpdate.getLastname()),
                () -> assertThat(updated.getSalary()).isEqualTo(toUpdate.getSalary())
        );
    }

    @Test
    void deleteByIdShouldDeleteEntity() {
        service.deleteById(EMPLOYEE_UUID);

        assertThatThrownBy(() -> service.findById(EMPLOYEE_UUID))
                .isInstanceOf(EntityNotFoundException.class);
    }
}