package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.kc_demo.dto.request.ContactInfoCreateDto;
import ru.clevertec.kc_demo.dto.response.ContactInfoReadDto;
import ru.clevertec.kc_demo.repository.api.ContactInfoRepository;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;
import ru.clevertec.kc_demo.service.ContactInfoService;
import ru.clevertec.kc_demo.service.CrudService;
import ru.clevertec.kc_demo.service.mapper.ContactInfoMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactInfoServiceImpl implements ContactInfoService {

    private final ContactInfoRepository repository;
    private final ContactInfoMapper mapper;

    @Override
    @Transactional
    public ContactInfoReadDto create(ContactInfoCreateDto createDto) {
        ContactInfo contactInfo = mapper.createDtoToEntity(createDto);
        return mapper.entityToReadDto(repository.save(contactInfo));
    }

    @Override
    public ContactInfoReadDto findById(Long id) {
        ContactInfo contactInfo = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.entityToReadDto(contactInfo);
    }

    @Override
    public Page<ContactInfoReadDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public ContactInfoReadDto updateById(Long id, ContactInfoCreateDto createDto) {
        ContactInfo contactInfo = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(contactInfo, createDto);

        return mapper.entityToReadDto(repository.save(contactInfo));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(); });
    }
}
