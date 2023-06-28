package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;
import ru.clevertec.kc_demo.repository.api.DepartmentRepository;
import ru.clevertec.kc_demo.repository.entity.Department;
import ru.clevertec.kc_demo.service.CrudService;
import ru.clevertec.kc_demo.service.DepartmentService;
import ru.clevertec.kc_demo.service.mapper.DepartmentMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    @Override
    @Transactional
    public DepartmentReadDto create(DepartmentCreateDto createDto) {
        Department department = mapper.createDtoToEntity(createDto);
        return mapper.entityToReadDto(repository.save(department));
    }

    @Override
    public DepartmentReadDto findById(Long id) {
        Department department = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.entityToReadDto(department);
    }

    @Override
    public Page<DepartmentReadDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public DepartmentReadDto updateById(Long id, DepartmentCreateDto createDto) {
        Department department = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(department, createDto);

        return mapper.entityToReadDto(repository.save(department));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(); });
    }
}
