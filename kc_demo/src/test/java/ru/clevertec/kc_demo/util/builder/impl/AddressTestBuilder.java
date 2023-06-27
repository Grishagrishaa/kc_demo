package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.response.AddressReadDto;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.repository.entity.City;
import ru.clevertec.kc_demo.util.builder.TestBuilder;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressTestBuilder implements TestBuilder<Address> {

    private Long id;
    private String street;
    private City city;

    public static AddressTestBuilder defaultValues(){
        AddressTestBuilder addressTestBuilder = new AddressTestBuilder();

        addressTestBuilder.setId(1L);
        addressTestBuilder.setStreet("Street Sample");
        addressTestBuilder.setCity(CityTestBuilder.defaultValues().build());

        return addressTestBuilder;
    }

    public AddressReadDto buildReadDto(){
        return AddressReadDto.builder()
                .setId(this.id)
                .setStreet(this.street)
                .setCity(CityTestBuilder.defaultValues().buildReadDto())
                .build();
    }

    public AddressCreateDto buildCreateDto(){
        return AddressCreateDto.builder()
                .setStreet(this.street)
                .setCity(CityTestBuilder.defaultValues().buildCreateDto())
                .build();
    }

    @Override
    public Address build() {
        return Address.builder()
                .setId(this.id)
                .setStreet(this.street)
                .setCity(this.city)
                .build();
    }
}
