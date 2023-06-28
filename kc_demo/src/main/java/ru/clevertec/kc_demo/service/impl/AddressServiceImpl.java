package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.response.AddressReadDto;
import ru.clevertec.kc_demo.repository.api.AddressRepository;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.service.AddressService;
import ru.clevertec.kc_demo.service.CrudService;
import ru.clevertec.kc_demo.service.mapper.AddressMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;

    @Override
    @Transactional
    public AddressReadDto create(AddressCreateDto createDto) {
        Address address = mapper.createDtoToEntity(createDto);
        return mapper.entityToReadDto(repository.save(address));
    }

    @Override
    public AddressReadDto findById(Long id) {
        Address address = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.entityToReadDto(address);
    }

    @Override
    public Page<AddressReadDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public AddressReadDto updateById(Long id, AddressCreateDto createDto) {
        Address address = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(address, createDto);

        return mapper.entityToReadDto(repository.save(address));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(); });
    }
}
