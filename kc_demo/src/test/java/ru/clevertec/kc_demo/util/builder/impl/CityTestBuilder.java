package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.response.CityReadDto;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.repository.entity.City;
import ru.clevertec.kc_demo.util.TestUtils;
import ru.clevertec.kc_demo.util.builder.TestBuilder;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityTestBuilder implements TestBuilder<City> {

    private Long id;
    private String name;
    private Long population;

    public static CityTestBuilder defaultValues(){
        CityTestBuilder cityTestBuilder = new CityTestBuilder();

        cityTestBuilder.setId(1L);
        cityTestBuilder.setName("Name Sample");
        cityTestBuilder.setPopulation(100_000L);

        return cityTestBuilder;
    }

    public static CityTestBuilder randomValues(){
        CityTestBuilder cityTestBuilder = new CityTestBuilder();

        cityTestBuilder.setId(TestUtils.getRandomLong());
        cityTestBuilder.setName(TestUtils.getRandomString());
        cityTestBuilder.setPopulation(TestUtils.getRandomLong());

        return cityTestBuilder;
    }

    public static CityReadDto toReadDto(City city){
        return CityReadDto.builder()
                .setId(city.getId())
                .setName(city.getName())
                .setPopulation(city.getPopulation())
                .build();
    }

    public static CityCreateDto toCreateDto(City city){
        return CityCreateDto.builder()
                .setName(city.getName())
                .setPopulation(city.getPopulation())
                .build();
    }


    @Override
    public City build() {
        return City.builder()
                .setId(this.id)
                .setName(this.name)
                .setPopulation(this.population)
                .build();
    }
}
