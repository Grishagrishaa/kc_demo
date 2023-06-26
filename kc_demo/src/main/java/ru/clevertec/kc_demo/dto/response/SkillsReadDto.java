package ru.clevertec.kc_demo.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class SkillsReadDto {

    Long id;
    String name;

}
