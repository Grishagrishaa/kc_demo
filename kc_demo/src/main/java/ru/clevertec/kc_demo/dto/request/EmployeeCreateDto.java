package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
public class EmployeeCreateDto {

    @Size(min = 2, max = 25)
    private String name;
    @Size(min = 2, max = 25)
    private String lastname;
    @Positive
    private Integer age;
    @PositiveOrZero
    private Long salary;

    @NotNull
    private AddressCreateDto address;

    @NotNull
    private Set<SkillsCreateDto> skills;

    @NotNull
    private DepartmentCreateDto department;

    @NotNull
    private ContactInfoCreateDto contactInfo;

}
