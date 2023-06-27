package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.response.CityReadDto;
import ru.clevertec.kc_demo.repository.api.CityRepository;
import ru.clevertec.kc_demo.repository.entity.City;
import ru.clevertec.kc_demo.service.CityService;
import ru.clevertec.kc_demo.service.CrudService;
import ru.clevertec.kc_demo.service.mapper.CityMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {

    private final CityRepository repository;
    private final CityMapper mapper;

    @Override
    @Transactional
    public CityReadDto create(CityCreateDto createDto) {
        City city = mapper.createDtoToEntity(createDto);
        return mapper.entityToReadDto(repository.save(city));
    }

    @Override
    public CityReadDto findById(Long id) {
        City city = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.entityToReadDto(city);
    }

    @Override
    public Page<CityReadDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public CityReadDto updateById(Long id, CityCreateDto createDto) {
        City city = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(city, createDto);

        return mapper.entityToReadDto(repository.save(city));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(); });
    }
}
