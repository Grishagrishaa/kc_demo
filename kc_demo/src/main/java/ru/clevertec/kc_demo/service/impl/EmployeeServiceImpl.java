package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.age;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.lastname;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.name;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.salary;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    @Transactional
    public EmployeeReadDto create(@Valid EmployeeCreateDto createDto) {
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
    public Page<EmployeeReadDto> findAllPageable(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    public Page<EmployeeReadDto> findAllByEmployeeFilter(EmployeeFilter filter, Pageable pageable) {
        Specification<Employee> spec = Specification.where(EmployeeSpec.like(name, filter.getName())
                .and(EmployeeSpec.like(lastname, filter.getLastname()))
                .and(EmployeeSpec.equals(age, filter.getAge()))
                .and(EmployeeSpec.equals(salary, filter.getSalary()))
                .and(EmployeeSpec.streetLike(filter.getStreet()))
                .and(EmployeeSpec.departmentLike(filter.getDepartmentName()))
                .and(EmployeeSpec.skillsIn(filter.getSkillsNames())));

        return repository.findAll(spec, pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public EmployeeReadDto updateById(UUID uuid, @Valid EmployeeCreateDto createUpdateDto) {
        Employee employee = repository.findById(uuid)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(employee, createUpdateDto);

        return mapper.entityToReadDto(repository.save(employee));
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        repository.findById(uuid).ifPresentOrElse(repository::delete, () -> { throw new EntityNotFoundException(); });
    }

}
