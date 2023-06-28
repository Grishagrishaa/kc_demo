package ru.clevertec.kc_demo.service;

import ru.clevertec.kc_demo.dto.request.AddressCreateDto;
import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.response.AddressReadDto;
import ru.clevertec.kc_demo.dto.response.CityReadDto;

public interface AddressService extends CrudService<AddressCreateDto, AddressReadDto>{
}
