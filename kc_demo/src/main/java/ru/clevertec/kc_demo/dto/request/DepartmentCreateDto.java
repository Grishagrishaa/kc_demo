package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class DepartmentCreateDto {

    @Size(min = 2, max = 25)
    String name;

    @Valid
    CityCreateDto city;

}
