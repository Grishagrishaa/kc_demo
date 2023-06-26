package ru.clevertec.kc_demo.controller.filters;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Builder
@FieldNameConstants
@AllArgsConstructor(staticName = "of")
public class EmployeeFilter {

    @Size(min = 1, max = 20)
    private String name;

    @Size(min = 1, max = 30)
    private String lastname;

    @Max(120)
    private Integer age;

    @Positive
    private Long salary;

    @Size(min = 1, max = 40)
    private String street;

    @Size(min = 1, max = 20)
    private List<String> skillsNames;

    @Size(min = 1, max = 20)
    private String departmentName;

    public static EmployeeFilter defaultValues() {
        return EmployeeFilter.builder().build();
    }

}
