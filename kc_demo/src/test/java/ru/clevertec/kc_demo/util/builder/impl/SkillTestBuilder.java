package ru.clevertec.kc_demo.util.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.kc_demo.dto.request.SkillCreateDto;
import ru.clevertec.kc_demo.dto.response.SkillReadDto;
import ru.clevertec.kc_demo.repository.entity.Skill;
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

    public SkillReadDto buildReadDto(){
        return SkillReadDto.builder()
                .setId(this.id)
                .setName(this.name)
                .build();
    }

    public SkillCreateDto buildCreateDto(){
        return SkillCreateDto.builder()
                .setName(this.name)
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
