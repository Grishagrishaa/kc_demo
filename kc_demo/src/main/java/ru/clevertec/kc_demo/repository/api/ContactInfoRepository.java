package ru.clevertec.kc_demo.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;

import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long>{

    Optional<ContactInfo> findByEmail(String email);
}
