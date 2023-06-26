package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
public class SkillsCreateDto {

    @Size(min = 2, max = 25)
    private String name;

}
