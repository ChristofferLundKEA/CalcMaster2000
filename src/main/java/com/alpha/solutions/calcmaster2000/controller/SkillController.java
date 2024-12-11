package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Skill;
import com.alpha.solutions.calcmaster2000.service.SkillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/addNewSkill")
    public String addSkill(@ModelAttribute Skill skill,
                           @RequestParam int employeeID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        skillService.addSkill(skill);
        session.setAttribute("lastEmployeeID", employeeID); // Gem employeeID i sessionen
        return "redirect:/updateEmployee/" + employeeID;
    }

    @PostMapping("/addNewSkillToAllSkills")
    public String addSkill(@ModelAttribute Skill skill, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        skillService.addSkill(skill);
        session.setAttribute("lastAddedSkill", skill); // Gem den sidst tilføjede skill i sessionen
        return "redirect:/allSkills";
    }

    @GetMapping("/delete/{skillID}")
    public String deleteSkill(@PathVariable int skillID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        skillService.deleteSkillById(skillID);
        session.removeAttribute("lastDeletedSkill"); // Fjern eventuelle gamle data relateret til skills
        session.setAttribute("lastDeletedSkillID", skillID); // Gem ID for den sidst slettede skill
        return "redirect:/allSkills";
    }

    @GetMapping("/allSkills")
    public String allSkills(Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        List<Skill> listOfSkills = skillService.getAllSkills();
        model.addAttribute("skills", listOfSkills);

        // Tilføj session-data til modellen for visning (hvis nødvendigt)
        if (session.getAttribute("lastAddedSkill") != null) {
            model.addAttribute("lastAddedSkill", session.getAttribute("lastAddedSkill"));
        }
        if (session.getAttribute("lastDeletedSkillID") != null) {
            model.addAttribute("lastDeletedSkillID", session.getAttribute("lastDeletedSkillID"));
        }

        return "allSkills";
    }
}

