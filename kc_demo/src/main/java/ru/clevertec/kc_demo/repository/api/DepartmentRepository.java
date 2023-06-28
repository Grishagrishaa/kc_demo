package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.kc_demo.repository.entity.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

    @EntityGraph(attributePaths = {"city"})
    Optional<Department> findByName(String name);

    @EntityGraph(attributePaths = {"city"})
    Optional<Department> findById(Long id);

    @EntityGraph(attributePaths = {"city"})
    Page<Department> findAll(Pageable pageable);

}
