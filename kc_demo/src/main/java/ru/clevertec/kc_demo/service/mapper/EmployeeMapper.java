package ru.clevertec.kc_demo.service.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;
import ru.clevertec.kc_demo.repository.api.AddressRepository;
import ru.clevertec.kc_demo.repository.api.CityRepository;
import ru.clevertec.kc_demo.repository.api.ContactInfoRepository;
import ru.clevertec.kc_demo.repository.api.DepartmentRepository;
import ru.clevertec.kc_demo.repository.api.SkillRepository;
import ru.clevertec.kc_demo.repository.entity.Employee;

import java.util.stream.Collectors;

@Mapper(uses = {AddressMapper.class, CityMapper.class, ContactInfoMapper.class, DepartmentMapper.class, SkillMapper.class},
        imports = Collectors.class,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class EmployeeMapper {

    @Autowired
    protected AddressRepository addressRepository;
    @Autowired
    protected CityRepository cityRepository;
    @Autowired
    protected ContactInfoRepository contactInfoRepository;
    @Autowired
    protected DepartmentRepository departmentRepository;
    @Autowired
    protected SkillRepository skillRepository;

    @Mapping(target = "address", expression = "java(addressRepository.findByStreet(createDto.getAddress().getStreet())\n" +
                                              ".orElseGet(() -> addressMapper.createDtoToEntity(createDto.getAddress())))")
    @Mapping(target = "department", expression = "java(departmentRepository.findByName(createDto.getDepartment().getName())\n" +
                                                 ".orElseGet(() -> departmentMapper.createDtoToEntity(createDto.getDepartment())))")
    @Mapping(target = "skills", expression = "java(createDto.getSkills().stream()\n" +
                                                 ".map(skill -> skillRepository.findByName(skill.getName()).orElseGet(() -> skillMapper.createDtoToEntity(skill)))\n" +
                                                 ".collect(Collectors.toSet()))")
    @Mapping(target = "contactInfo", expression = "java(contactInfoRepository.findByEmail(createDto.getContactInfo().getEmail())\n" +
                                                  ".orElseGet(() -> contactInfoMapper.createDtoToEntity(createDto.getContactInfo())))")
    public abstract Employee createDtoToEntity(EmployeeCreateDto createDto);

    public abstract EmployeeReadDto entityToReadDto(Employee employee);

    @InheritConfiguration
    public abstract void update(@MappingTarget Employee employee, EmployeeCreateDto createDto);

}
