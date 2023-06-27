package ru.clevertec.kc_demo.service;

import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;

public interface DepartmentService extends CrudService<DepartmentCreateDto, DepartmentReadDto>{
}
