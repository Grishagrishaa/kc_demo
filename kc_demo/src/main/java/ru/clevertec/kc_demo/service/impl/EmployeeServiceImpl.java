package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;
import ru.clevertec.kc_demo.repository.api.EmployeeRepository;
import ru.clevertec.kc_demo.repository.entity.Employee;
import ru.clevertec.kc_demo.repository.spec.EmployeeSpec;
import ru.clevertec.kc_demo.service.EmployeeService;
import ru.clevertec.kc_demo.service.mapper.EmployeeMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    @Transactional
    public EmployeeReadDto create(EmployeeCreateDto createDto) {
        Employee employee = mapper.createDtoToEntity(createDto);
        return mapper.entityToReadDto(repository.save(employee));
    }

    @Override
    public EmployeeReadDto findById(UUID uuid) {
        Employee employee = repository.findById(uuid)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.entityToReadDto(employee);
    }

    @Override
    public Page<EmployeeReadDto> findAllByEmployeeFilter(EmployeeFilter filter, Pageable pageable) {
        Specification<Employee> spec = EmployeeSpec.buildSpec(filter);
        return repository.findAll(spec, pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public EmployeeReadDto updateByUuid(UUID uuid, EmployeeCreateDto createUpdateDto) {
        Employee employee = repository.findById(uuid)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(employee, createUpdateDto);

        return mapper.entityToReadDto(repository.save(employee));
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        repository.findById(uuid)
                .ifPresentOrElse(repository::delete,
                                    () -> { throw new EntityNotFoundException(); });
    }

}
