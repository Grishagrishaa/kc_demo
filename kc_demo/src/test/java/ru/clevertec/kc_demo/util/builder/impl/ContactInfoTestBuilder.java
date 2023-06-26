package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.ContactInfoCreateDto;
import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.ContactInfoReadDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;
import ru.clevertec.kc_demo.repository.entity.City;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;
import ru.clevertec.kc_demo.util.TestUtils;
import ru.clevertec.kc_demo.util.builder.TestBuilder;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoTestBuilder implements TestBuilder<ContactInfo> {

    private Long id;
    private String email;
    private String phone;
    private City city;

    public static ContactInfoTestBuilder defaultValues(){
        ContactInfoTestBuilder contactInfoTestBuilder = new ContactInfoTestBuilder();

        contactInfoTestBuilder.setId(1L);
        contactInfoTestBuilder.setEmail("NameSample@gmail.com");
        contactInfoTestBuilder.setPhone("+375299802357");
        contactInfoTestBuilder.setCity(CityTestBuilder.defaultValues().build());

        return contactInfoTestBuilder;
    }

    public static ContactInfoReadDto toReadDto(ContactInfo contactInfo){
        return ContactInfoReadDto.builder()
                .setId(contactInfo.getId())
                .setEmail(contactInfo.getEmail())
                .setPhone(contactInfo.getPhone())
                .setCity(CityTestBuilder.toReadDto(contactInfo.getCity()))
                .build();
    }

    public static ContactInfoCreateDto toCreateDto(ContactInfo contactInfo){
        return ContactInfoCreateDto.builder()
                .setEmail(contactInfo.getEmail())
                .setPhone(contactInfo.getPhone())
                .setCity(CityTestBuilder.toCreateDto(contactInfo.getCity()))
                .build();
    }

    @Override
    public ContactInfo build() {
        return ContactInfo.builder()
                .setId(this.id)
                .setEmail(this.email)
                .setPhone(this.phone)
                .setCity(this.city)
                .build();
    }
}
