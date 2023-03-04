package com.example.search_app.capston.services.impl;

import com.example.search_app.capston.models.Skill;
import com.example.search_app.capston.repositories.SkillRepository;
import com.example.search_app.capston.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public void addSkill(String name) {
        // checking the skill name is present or not.
        skillRepository.findByName(name)
                .ifPresent(skill -> {throw new RuntimeException("Skill name is already exist.");});
        Skill skill = new Skill();
        skill.setName(name);
        skillRepository.save(skill) ;
    }

    @Override
    public void updateSkill(long skillId, String newSkillName) {
        Skill skill = getSkillById(skillId);
        if (skill.getName().equalsIgnoreCase(newSkillName)) {
            throw new RuntimeException("You provided same skill name.");
        }
        skill.setName(newSkillName);
        skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(long skillId) {
        Skill skillById = getSkillById(skillId);
        skillRepository.delete(skillById);
    }

    @Override
    public Skill getSkillById(long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with id:" + skillId));
    }

    @Override
    public List<Skill> getAllSkillByIds(Set<Long> ids) {
        return skillRepository.findAllById(ids);
    }
}
