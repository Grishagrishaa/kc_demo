package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.CityCreateDto;
import ru.clevertec.kc_demo.dto.request.SkillsCreateDto;
import ru.clevertec.kc_demo.dto.response.SkillsReadDto;
import ru.clevertec.kc_demo.repository.entity.Skill;
import ru.clevertec.kc_demo.util.TestUtils;
import ru.clevertec.kc_demo.util.builder.TestBuilder;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillTestBuilder implements TestBuilder<Skill> {

    private Long id;
    private String name;

    public static SkillTestBuilder defaultValues(){
        SkillTestBuilder skillTestBuilder = new SkillTestBuilder();

        skillTestBuilder.setId(1L);
        skillTestBuilder.setName("Name Sample");

        return skillTestBuilder;
    }

    public static SkillTestBuilder randomValues(){
        SkillTestBuilder skillTestBuilder = new SkillTestBuilder();

        skillTestBuilder.setId(TestUtils.getRandomLong());
        skillTestBuilder.setName(TestUtils.getRandomString());

        return skillTestBuilder;
    }

    public static SkillsReadDto toReadDto(Skill city){
        return SkillsReadDto.builder()
                .setId(city.getId())
                .setName(city.getName())
                .build();
    }

    public static SkillsCreateDto toCreateDto(Skill skill){
        return SkillsCreateDto.builder()
                .setName(skill.getName())
                .build();
    }

    @Override
    public Skill build() {
        return Skill.builder()
                .setId(this.id)
                .setName(this.name)
                .build();
    }
}
