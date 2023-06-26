package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.clevertec.kc_demo.repository.entity.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

    @EntityGraph(value = "withAddressAndSkillsAndDepartmentAndContactInfo")
    Optional<Employee> findById(UUID uuid);

    @EntityGraph(value = "withAddressAndSkillsAndDepartmentAndContactInfo")
    Page<Employee> findAll(Specification<Employee> specification, Pageable pageable);

}
