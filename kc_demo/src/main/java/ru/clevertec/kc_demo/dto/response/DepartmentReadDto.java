package ru.clevertec.kc_demo.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class DepartmentReadDto {

    Long id;
    String name;
    CityReadDto city;

}
