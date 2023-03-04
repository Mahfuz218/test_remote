package com.example.search_app.capston.services;

import com.example.search_app.capston.models.Skill;
import org.springframework.stereotype.Service;

public interface SkillService {
    void addSkill(String name);
    void updateSkill(long skillId, String newSkillName);
    void deleteSkill(long skillId);
    Skill getSkillById(long skillId);
}
