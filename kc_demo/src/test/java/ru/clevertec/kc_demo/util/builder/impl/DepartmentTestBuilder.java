package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;
import ru.clevertec.kc_demo.repository.entity.City;
import ru.clevertec.kc_demo.repository.entity.Department;
import ru.clevertec.kc_demo.util.TestUtils;
import ru.clevertec.kc_demo.util.builder.TestBuilder;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTestBuilder implements TestBuilder<Department> {

    private Long id;
    private String name;
    private City city;

    public static DepartmentTestBuilder defaultValues(){
        DepartmentTestBuilder departmentTestBuilder = new DepartmentTestBuilder();

        departmentTestBuilder.setId(1L);
        departmentTestBuilder.setName("Name Sample");
        departmentTestBuilder.setCity(CityTestBuilder.defaultValues().build());

        return departmentTestBuilder;
    }

    public DepartmentReadDto buildReadDto(){
        return DepartmentReadDto.builder()
                .setId(this.id)
                .setName(this.name)
                .setCity(CityTestBuilder.defaultValues().buildReadDto())
                .build();
    }

    public DepartmentCreateDto buildCreateDto(){
        return DepartmentCreateDto.builder()
                .setName(this.name)
                .setCity(CityTestBuilder.defaultValues().buildCreateDto())
                .build();
    }

    @Override
    public Department build() {
        return Department.builder()
                .setId(this.id)
                .setName(this.name)
                .setCity(this.city)
                .build();
    }
}
