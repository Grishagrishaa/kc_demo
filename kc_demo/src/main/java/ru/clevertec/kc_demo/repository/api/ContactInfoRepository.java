package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;

import java.util.Optional;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long>{

    @EntityGraph(attributePaths = {"city"})
    Optional<ContactInfo> findByEmail(String email);

    @EntityGraph(attributePaths = {"city"})
    Optional<ContactInfo> findById(Long id);

    @EntityGraph(attributePaths = {"city"})
    Page<ContactInfo> findAll(Pageable pageable);
}
