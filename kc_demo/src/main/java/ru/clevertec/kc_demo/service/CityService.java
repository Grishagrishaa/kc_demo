package ru.clevertec.kc_demo.service;

import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.response.CityReadDto;

public interface CityService extends CrudService<CityCreateDto, CityReadDto>{
}
