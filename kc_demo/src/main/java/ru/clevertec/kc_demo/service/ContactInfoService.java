package ru.clevertec.kc_demo.service;

import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.request.ContactInfoCreateDto;
import ru.clevertec.kc_demo.dto.response.AddressReadDto;
import ru.clevertec.kc_demo.dto.response.ContactInfoReadDto;

public interface ContactInfoService extends CrudService<ContactInfoCreateDto, ContactInfoReadDto>{
}
