package ru.clevertec.kc_demo.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class AddressReadDto {

    Long id;
    String street;
    CityReadDto city;

}
