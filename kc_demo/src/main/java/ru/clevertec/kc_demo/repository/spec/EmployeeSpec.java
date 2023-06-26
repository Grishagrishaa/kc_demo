package ru.clevertec.kc_demo.repository.spec;

import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.kc_demo.controller.filters.EmployeeFilter;
import ru.clevertec.kc_demo.repository.entity.Address;
import ru.clevertec.kc_demo.repository.entity.Department;
import ru.clevertec.kc_demo.repository.entity.Employee;
import ru.clevertec.kc_demo.repository.entity.Skill;

import java.util.List;

import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.age;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.lastname;
import static ru.clevertec.kc_demo.controller.filters.EmployeeFilter.Fields.salary;

@UtilityClass
public class EmployeeSpec {

    public static Specification<Employee> hasMatchWithFilter(EmployeeFilter filter) {
        return Specification.allOf(
                like(lastname, filter.getLastname()),
                equals(age, filter.getAge()),
                equals(salary, filter.getSalary()),
                streetLike(filter.getStreet()),
                departmentLike(filter.getDepartmentName()),
                skillsIn(filter.getSkillsNames())
        );
    }

    public static Specification<Employee> like(String fieldName, String fieldValue){
        return (root, query, criteriaBuilder)
                -> {
            if(fieldValue == null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.like(root.get(fieldName), "%"+fieldValue+"%");
        };
    }

    public static Specification<Employee> equals(String fieldName, Number fieldValue){
        return (root, query, criteriaBuilder)
                -> {
            if(fieldValue == null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get(fieldName), fieldValue);
        };
    }

    public static Specification<Employee> streetLike(String streetName){
        return (root, query, criteriaBuilder)
                -> {
            if(streetName == null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            Join<Address, Employee> join = root.join("address");

            return criteriaBuilder.like(join.get("street"), "%"+streetName+"%");
        };
    }

    public static Specification<Employee> departmentLike(String departmentName){
        return (root, query, criteriaBuilder)
                -> {
            if(departmentName == null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            Join<Department, Employee> join = root.join("department");

            return criteriaBuilder.like(join.get("name"), "%"+departmentName+"%");
        };
    }

    public static Specification<Employee> skillsIn(List<String> skillsNames){
        return (root, query, criteriaBuilder)
                -> {
            if(skillsNames == null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return root.join("skills").get("name").in(skillsNames);
        };
    }

}
