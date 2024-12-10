package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Skill;
import com.alpha.solutions.calcmaster2000.service.SkillService;
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
                           @RequestParam int employeeID) {
        skillService.addSkill(skill);
        return "redirect:/updateEmployee/" + employeeID;// employeeID kommer fra et forrige side, så man bliver redirected tilbage til den side man kommer fra.
    }

    @PostMapping("/addNewSkillToAllSkills")
    public String addSkill(@ModelAttribute Skill skill) {
        skillService.addSkill(skill);
        return "redirect:/allSkills";
    }

    @GetMapping("/delete/{skillID}")
    public String deleteSkill(@PathVariable int skillID) {
        skillService.deleteSkillById(skillID);
        return "redirect:/allSkills";
    }



    @GetMapping("/allSkills")
    public String allSkills(Model model){
        List<Skill> listOfSkills = skillService.getAllSkills();
        model.addAttribute("skills", listOfSkills);
        return "allSkills";
    }
}
