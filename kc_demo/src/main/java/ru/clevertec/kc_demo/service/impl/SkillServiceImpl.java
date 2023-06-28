package ru.clevertec.kc_demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.kc_demo.dto.request.SkillCreateDto;
import ru.clevertec.kc_demo.dto.response.SkillReadDto;
import ru.clevertec.kc_demo.repository.api.SkillRepository;
import ru.clevertec.kc_demo.repository.entity.Skill;
import ru.clevertec.kc_demo.service.SkillService;
import ru.clevertec.kc_demo.service.mapper.SkillMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;
    private final SkillMapper mapper;

    @Override
    @Transactional
    public SkillReadDto create(SkillCreateDto createDto) {
        Skill skill = mapper.createDtoToEntity(createDto);
        return mapper.entityToReadDto(repository.save(skill));
    }

    @Override
    public SkillReadDto findById(Long id) {
        Skill skill = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.entityToReadDto(skill);
    }

    @Override
    public Page<SkillReadDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToReadDto);
    }

    @Override
    @Transactional
    public SkillReadDto updateById(Long id, SkillCreateDto createDto) {
        Skill skill = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.update(skill, createDto);

        return mapper.entityToReadDto(repository.save(skill));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(); });
    }
}
