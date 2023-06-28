package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "set")
public class ContactInfoCreateDto {

    @Email
    String email;

    @Size(min = 5, max = 12)
    String phone;

    @Valid
    CityCreateDto city;

}
