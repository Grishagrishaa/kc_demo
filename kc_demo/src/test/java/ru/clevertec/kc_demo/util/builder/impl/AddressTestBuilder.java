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

    public static AddressTestBuilder randomValues(){
        AddressTestBuilder addressTestBuilder = new AddressTestBuilder();

        addressTestBuilder.setId(1L);
        addressTestBuilder.setStreet("Street Sample");
        addressTestBuilder.setCity(CityTestBuilder.randomValues().build());

        return addressTestBuilder;
    }

    public static AddressReadDto toReadDto(Address address){
        return AddressReadDto.builder()
                .setId(address.getId())
                .setStreet(address.getStreet())
                .setCity(CityTestBuilder.toReadDto(address.getCity()))
                .build();
    }

    public static AddressCreateDto toCreateDto(Address address){
        return AddressCreateDto.builder()
                .setStreet(address.getStreet())
                .setCity(CityTestBuilder.toCreateDto(address.getCity()))
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
