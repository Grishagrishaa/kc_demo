package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.kc_demo.repository.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>{

    @EntityGraph(attributePaths = {"city"})
    Optional<Address> findByStreet(String streetName);

    @EntityGraph(attributePaths = {"city"})
    Optional<Address> findById(Long id);

    @EntityGraph(attributePaths = {"city"})
    Page<Address> findAll(Pageable pageable);
}
