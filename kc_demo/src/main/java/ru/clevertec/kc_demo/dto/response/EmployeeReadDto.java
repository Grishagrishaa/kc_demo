package ru.clevertec.kc_demo.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(setterPrefix = "set")
public class EmployeeReadDto {

    UUID id;
    Instant createdDate;
    String name;
    String lastname;
    Integer age;
    Long salary;
    AddressReadDto address;
    Set<SkillReadDto> skills;
    DepartmentReadDto department;
    ContactInfoReadDto contactInfo;

}
