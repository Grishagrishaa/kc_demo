package ru.clevertec.kc_demo.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@SuperBuilder(setterPrefix = "set")
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "employee", schema = "employees")
@NamedEntityGraph(name = "withAddressAndSkillsAndDepartmentAndContactInfo",
                    attributeNodes = {@NamedAttributeNode(value = "address", subgraph = "cities"),
                                      @NamedAttributeNode(value = "contactInfo", subgraph = "cities"),
                                      @NamedAttributeNode(value = "department", subgraph = "cities"),
                                      @NamedAttributeNode(value = "skills")},
                    subgraphs = @NamedSubgraph(name = "cities", attributeNodes = @NamedAttributeNode("city")))
public class Employee extends AuditingEntity<UUID>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
    private String lastname;
    private Integer age;
    private Long salary;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "employee_skill",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id")},
            schema = "employees")
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Department department;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ContactInfo contactInfo;

}
