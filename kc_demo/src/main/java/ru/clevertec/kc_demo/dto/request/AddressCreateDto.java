package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class AddressCreateDto {

    @Size(min = 3, max = 25)
    String street;

    @NotNull
    CityCreateDto city;

}
