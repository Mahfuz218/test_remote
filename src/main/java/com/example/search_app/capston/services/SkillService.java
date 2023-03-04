package com.example.search_app.capston.services;

import com.example.search_app.capston.models.Skill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface SkillService {
    void addSkill(String name);
    void updateSkill(long skillId, String newSkillName);
    void deleteSkill(long skillId);
    Skill getSkillById(long skillId);

    List<Skill> getAllSkillByIds(Set<Long> ids);
}
