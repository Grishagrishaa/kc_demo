package ru.clevertec.kc_demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.service.EmployeeService;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;

import java.util.UUID;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService service;

    /**
     * Creates a new employee based on the provided createDto.
     *
     * @param createDto the DTO containing the data for creating an employee
     * @return the ResponseEntity with the created employee DTO in the response body
     */
    @PostMapping
    public ResponseEntity<EmployeeReadDto> create(@RequestBody EmployeeCreateDto createDto){
        return ResponseEntity.status(CREATED)
                .body(service.create(createDto));
    }

    /**
     * Retrieves an employee by their UUID.
     *
     * @param uuid the UUID of the employee to retrieve
     * @return the ResponseEntity with the retrieved employee DTO in the response body
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeReadDto> findById(@PathVariable UUID uuid){
        return ResponseEntity.status(OK)
                .body(service.findById(uuid));
    }

    /**
     * Retrieves all employees using pagination.
     *
     * @param pageable the Pageable object defining the pagination information
     * @return the ResponseEntity with the page of employee DTOs in the response body
     */
    @GetMapping
    public ResponseEntity<Page<EmployeeReadDto>> findAllPageable(@PageableDefault Pageable pageable){
        return ResponseEntity.status(OK)
                .body(service.findAllPageable(pageable));
    }

    /**
     * Retrieves all employees that match the specified filter using pagination.
     *
     * @param filter   the EmployeeFilter object containing the filter criteria
     * @param pageable the Pageable object defining the pagination information
     * @return the ResponseEntity with the page of employee DTOs matching the filter in the response body
     */
    @GetMapping("/filtered")
    public ResponseEntity<Page<EmployeeReadDto>> findAllByEmployeeFilter(@Valid EmployeeFilter filter, @PageableDefault Pageable pageable){
        return ResponseEntity.status(OK)
                .body(service.findAllByEmployeeFilter(filter, pageable));
    }

    /**
     * Updates an employee identified by their UUID.
     *
     * @param uuid the UUID of the employee to update
     * @return the ResponseEntity with the updated employee DTO in the response body
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<EmployeeReadDto> updateById(@PathVariable UUID uuid, @Valid @RequestBody EmployeeCreateDto createUpdateDto){
        return ResponseEntity.status(ACCEPTED)
                .body(service.updateById(uuid, createUpdateDto));
    }

    /**
     * Deletes an employee identified by their UUID.
     *
     * @param uuid the UUID of the employee to delete
     * @return the ResponseEntity indicating a successful deletion
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteById(@PathVariable UUID uuid){
        service.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

}
