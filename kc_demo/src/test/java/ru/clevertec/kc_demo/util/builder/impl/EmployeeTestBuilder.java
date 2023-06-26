package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.EmployeeCreateDto;
import ru.clevertec.kc_demo.dto.response.EmployeeReadDto;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.repository.entity.ContactInfo;
import ru.clevertec.kc_demo.repository.entity.Department;
import ru.clevertec.kc_demo.repository.entity.Employee;
import ru.clevertec.kc_demo.repository.entity.Skill;
import ru.clevertec.kc_demo.util.TestUtils;
import ru.clevertec.kc_demo.util.builder.TestBuilder;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTestBuilder implements TestBuilder<Employee> {

    private UUID id;
    private Instant createdDate;
    private Instant updatedDate;
    private String createdBy;
    private String modifiedBy;

    private String name;
    private String lastname;
    private Integer age;
    private Long salary;
    private Address address;
    private Set<Skill> skills = new HashSet<>();
    private Department department;
    private ContactInfo contactInfo;


    public static EmployeeTestBuilder defaultValues(){
        EmployeeTestBuilder employeeTestBuilder = new EmployeeTestBuilder();

        employeeTestBuilder.setId(UUID.fromString("96966e15-fc8f-4c53-9056-ccbf36a865f9"));
        employeeTestBuilder.setCreatedDate(Instant.MAX);
        employeeTestBuilder.setUpdatedDate(Instant.MAX);
        employeeTestBuilder.setCreatedBy("CreatedBy sample");
        employeeTestBuilder.setModifiedBy("ModifiedBy sample");
        employeeTestBuilder.setName("BOSS");
        employeeTestBuilder.setLastname("BOSS LASTNAME");
        employeeTestBuilder.setAge(18);
        employeeTestBuilder.setSalary(800L);
        employeeTestBuilder.setAddress(AddressTestBuilder.defaultValues().build());
        employeeTestBuilder.setSkills(Set.of(SkillTestBuilder.defaultValues().build()));
        employeeTestBuilder.setDepartment(DepartmentTestBuilder.defaultValues().build());
        employeeTestBuilder.setContactInfo(ContactInfoTestBuilder.defaultValues().build());

        return employeeTestBuilder;
    }

    public static EmployeeTestBuilder randomValues(){
        EmployeeTestBuilder employeeTestBuilder = new EmployeeTestBuilder();

        employeeTestBuilder.setId(UUID.randomUUID());
        employeeTestBuilder.setCreatedDate(Instant.now());
        employeeTestBuilder.setUpdatedDate(Instant.now());
        employeeTestBuilder.setCreatedBy(TestUtils.getRandomString());
        employeeTestBuilder.setModifiedBy(TestUtils.getRandomString());
        employeeTestBuilder.setName(TestUtils.getRandomString());
        employeeTestBuilder.setLastname(TestUtils.getRandomString());
        employeeTestBuilder.setAge(TestUtils.getRandomInt());
        employeeTestBuilder.setSalary(TestUtils.getRandomLong());
        employeeTestBuilder.setAddress(AddressTestBuilder.randomValues().build());
        employeeTestBuilder.setSkills(Set.of(SkillTestBuilder.randomValues().build()));
        employeeTestBuilder.setDepartment(DepartmentTestBuilder.randomValues().build());
        employeeTestBuilder.setContactInfo(ContactInfoTestBuilder.randomValues().build());

        return employeeTestBuilder;
    }

    public EmployeeCreateDto buildCreateDto(){
        return EmployeeCreateDto.builder()
                .setName(this.name)
                .setLastname(this.lastname)
                .setAge(this.age)
                .setSalary(this.salary)
                .setAddress(AddressTestBuilder.toCreateDto(this.address))
                .setSkills(this.skills.stream().map(SkillTestBuilder::toCreateDto).collect(Collectors.toSet()))
                .setDepartment(DepartmentTestBuilder.toCreateDto(this.department))
                .setContactInfo(ContactInfoTestBuilder.toCreateDto(this.contactInfo))
                .build();
    }

    public static EmployeeCreateDto toCreateDto(Employee employee){
        return EmployeeCreateDto.builder()
                .setName(employee.getName())
                .setLastname(employee.getLastname())
                .setAge(employee.getAge())
                .setSalary(employee.getSalary())
                .setAddress(AddressTestBuilder.toCreateDto(employee.getAddress()))
                .setSkills(employee.getSkills().stream().map(SkillTestBuilder::toCreateDto).collect(Collectors.toSet()))
                .setDepartment(DepartmentTestBuilder.toCreateDto(employee.getDepartment()))
                .setContactInfo(ContactInfoTestBuilder.toCreateDto(employee.getContactInfo()))
                .build();
    }

    public EmployeeReadDto buildReadDto(){
        return EmployeeReadDto.builder()
                .setId(this.id)
                .setCreatedDate(this.createdDate)
                .setName(this.name)
                .setLastname(this.lastname)
                .setAge(this.age)
                .setSalary(this.salary)
                .setAddress(AddressTestBuilder.toReadDto(this.address))
                .setSkills(this.skills.stream().map(SkillTestBuilder::toReadDto).collect(Collectors.toSet()))
                .setDepartment(DepartmentTestBuilder.toReadDto(this.department))
                .setContactInfo(ContactInfoTestBuilder.toReadDto(this.contactInfo))
                .build();
    }

    public static EmployeeReadDto toReadDto(Employee employee){
        return EmployeeReadDto.builder()
                .setId(employee.getId())
                .setCreatedDate(employee.getCreatedDate())
                .setName(employee.getName())
                .setLastname(employee.getLastname())
                .setAge(employee.getAge())
                .setSalary(employee.getSalary())
                .setAddress(AddressTestBuilder.toReadDto(employee.getAddress()))
                .setSkills(employee.getSkills().stream().map(SkillTestBuilder::toReadDto).collect(Collectors.toSet()))
                .setDepartment(DepartmentTestBuilder.toReadDto(employee.getDepartment()))
                .setContactInfo(ContactInfoTestBuilder.toReadDto(employee.getContactInfo()))
                .build();
    }


    @Override
    public Employee build() {
        return Employee.builder()
                .setId(this.id)
                .setCreatedDate(this.createdDate)
                .setUpdatedDate(this.updatedDate)
                .setCreatedBy(this.createdBy)
                .setModifiedBy(this.modifiedBy)
                .setName(this.name)
                .setLastname(this.lastname)
                .setAge(this.age)
                .setSalary(this.salary)
                .setAddress(this.address)
                .setSkills(this.skills)
                .setDepartment(this.department)
                .setContactInfo(this.contactInfo)
                .build();
    }
}
