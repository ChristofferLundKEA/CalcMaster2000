package com.alpha.solutions.calcmaster2000.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SkillTest {

        @Test
        void testEqualsAndHashCode() {
            Skill skill1 = new Skill();
            skill1.setSkillID(1);

            Skill skill2 = new Skill();
            skill2.setSkillID(1);

            Skill skill3 = new Skill();
            skill3.setSkillID(2);

            assertEquals(skill1, skill2);
            assertNotEquals(skill1, skill3);

            assertEquals(skill1.hashCode(), skill2.hashCode());
            assertNotEquals(skill1.hashCode(), skill3.hashCode());
        }

}
