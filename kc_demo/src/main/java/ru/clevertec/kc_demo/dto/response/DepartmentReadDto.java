package ru.clevertec.kc_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.kc_demo.repository.entity.City;

import java.time.Instant;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
public class DepartmentReadDto {

    private Long id;

    private String name;
    private CityReadDto city;

}
