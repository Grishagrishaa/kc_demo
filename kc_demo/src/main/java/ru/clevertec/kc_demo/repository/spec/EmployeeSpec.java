package ru.clevertec.kc_demo.repository.spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.repository.entity.Employee;

import java.util.List;

import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.age;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.lastname;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.name;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.salary;

@UtilityClass
public class EmployeeSpec {

    public static Specification<Employee> buildSpec(EmployeeFilter filter) {


        return Specification.allOf(
                filter.getName() != null ? like(name, filter.getName()) : null,
                filter.getLastname() != null ? like(lastname, filter.getLastname()) : null,
                filter.getAge() != null ? equals(age, filter.getAge()) : null,
                filter.getSalary() != null ? equals(salary, filter.getSalary()) : null,
                filter.getStreet() != null ? streetLike(filter.getStreet()) : null,
                filter.getDepartmentName() != null ? departmentLike(filter.getDepartmentName()) : null,
                filter.getSkillsNames() != null ? skillsIn(filter.getSkillsNames()) : null
        );
    }

    public static Specification<Employee> like(String fieldName, String fieldValue){
        return (root, query, cb) -> cb.like(root.get(fieldName), "%"+fieldValue+"%");
    }

    public static Specification<Employee> equals(String fieldName, Number fieldValue){
        return (root, query, cb) -> cb.equal(root.get(fieldName), fieldValue);
    }

    public static Specification<Employee> streetLike(String streetName){
        return (root, query, cb) -> cb.like(root.join("address").get("street"), "%"+streetName+"%");
    }

    public static Specification<Employee> departmentLike(String departmentName){
        return (root, query, cb) -> cb.like(root.join("department").get("name"), "%"+departmentName+"%");
    }

    public static Specification<Employee> skillsIn(List<String> skillsNames){
        return (root, query, cb) -> root.join("skills").get("name").in(skillsNames);
    }

}
