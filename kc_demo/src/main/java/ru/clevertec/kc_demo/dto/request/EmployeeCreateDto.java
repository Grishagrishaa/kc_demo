package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder(setterPrefix = "set")
public class EmployeeCreateDto {

    @Size(min = 2, max = 25)
    String name;

    @Size(min = 2, max = 25)
    String lastname;

    @Positive
    Integer age;

    @PositiveOrZero
    Long salary;

    @Valid AddressCreateDto address;

    @Valid Set<SkillsCreateDto> skills;

    @Valid DepartmentCreateDto department;

    @Valid ContactInfoCreateDto contactInfo;

}
