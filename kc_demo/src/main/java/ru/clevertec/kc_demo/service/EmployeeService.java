package ru.clevertec.kc_demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;

import java.util.UUID;

/**
 * Service interface for managing employees.
 */
public interface EmployeeService {

    /**
     * Creates a new employee based on the provided createDto.
     *
     * @param createDto the DTO containing the data for creating an employee
     * @return the DTO representing the created employee
     */
    EmployeeReadDto create(EmployeeCreateDto createDto);

    /**
     * Retrieves an employee by their UUID.
     *
     * @param uuid the UUID of the employee to retrieve
     * @return the DTO representing the retrieved employee
     */
    EmployeeReadDto findById(UUID uuid);


    /**
     * Retrieves all employees that match the specified filter using pagination.
     *
     * @param filter   the filter criteria for retrieving employees
     * @param pageable the pagination information
     * @return the DTO representing the page of employees matching the filter
     */
    Page<EmployeeReadDto> findAllByEmployeeFilter(EmployeeFilter filter, Pageable pageable);

    /**
     * Updates an employee identified by their UUID based on the provided data.
     *
     * @param uuid            the UUID of the employee to update
     * @param createUpdateDto - DTO with data to update
     * @return the DTO representing the updated employee
     */
    EmployeeReadDto updateByUuid(UUID uuid, EmployeeCreateDto createUpdateDto);

    /**
     * Deletes an employee identified by their UUID.
     *
     * @param uuid the UUID of the employee to delete
     */
    void deleteByUuid(UUID uuid);
}