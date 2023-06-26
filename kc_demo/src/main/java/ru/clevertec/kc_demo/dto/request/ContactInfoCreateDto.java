package ru.clevertec.kc_demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
public class ContactInfoCreateDto {

    @Email
    private String email;

    @Size(min = 5, max = 12)
    private String phone;

    @NotNull
    private CityCreateDto city;

}
