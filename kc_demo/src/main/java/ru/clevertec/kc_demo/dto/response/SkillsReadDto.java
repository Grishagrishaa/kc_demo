package ru.clevertec.kc_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
public class SkillsReadDto {

    private Long id;

    private String name;

}
