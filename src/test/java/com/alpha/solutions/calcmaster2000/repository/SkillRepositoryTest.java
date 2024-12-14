package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SkillRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;

    private Skill testSkill;

    @BeforeEach
    void setUp() {
        testSkill = new Skill();
        testSkill.setName("Java Programming");
        testSkill.setDescription("Skill for Java development");
    }

    @Test
    void testAddSkill() {
        skillRepository.addSkill(testSkill);

        List<Skill> skills = skillRepository.getAllSkills();
        assertFalse(skills.isEmpty());
        assertEquals("Java Programming", skills.getLast().getName());
    }

    @Test
    void testGetAllSkills() {
        List<Skill> skills = skillRepository.getAllSkills();
        assertEquals(20, skills.size());
        assertEquals("Technical Writing", skills.getLast().getName());
    }

    @Test
    void testDeleteSkillById() {
        List<Skill> skills = skillRepository.getAllSkills();
        int skillId = skills.getFirst().getSkillID();

        skillRepository.deleteSkillById(skillId);

        skills = skillRepository.getAllSkills();
        assertEquals(19, skills.size());
    }
}
