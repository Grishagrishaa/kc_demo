package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.kc_demo.repository.entity.Skill;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long>{

    Optional<Skill> findByName(String name);

}
