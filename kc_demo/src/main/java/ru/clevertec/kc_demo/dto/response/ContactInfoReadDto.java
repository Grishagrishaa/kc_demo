package ru.clevertec.kc_demo.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class ContactInfoReadDto {

    Long id;
    String email;
    String phone;
    CityReadDto city;

}
