package ru.clevertec.kc_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
public class EmployeeReadDto {

    private UUID id;
    private Instant createdDate;

    private String name;
    private String lastname;
    private Integer age;
    private Long salary;

    private AddressReadDto address;

    private Set<SkillsReadDto> skills;

    private DepartmentReadDto department;

    private ContactInfoReadDto contactInfo;

}
