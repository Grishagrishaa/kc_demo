package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.kc_demo.repository.entity.Address;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

    Optional<Address> findByStreet(String streetName);
}
