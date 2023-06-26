package ru.clevertec.kc_demo.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@SuperBuilder(setterPrefix = "set")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "employee", schema = "employees")
@NamedEntityGraph(name = "withAddressAndSkillsAndDepartmentAndContactInfo",
                    attributeNodes = {@NamedAttributeNode(value = "address", subgraph = "cities"),
                                      @NamedAttributeNode(value = "contactInfo", subgraph = "cities"),
                                      @NamedAttributeNode(value = "department", subgraph = "cities"),
                                      @NamedAttributeNode(value = "skills")},
                    subgraphs = @NamedSubgraph(name = "cities", attributeNodes = @NamedAttributeNode("city")))
public class Employee extends AuditingEntity<UUID>{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String lastname;
    private Integer age;
    private Long salary;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Department department;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ContactInfo contactInfo;

}
