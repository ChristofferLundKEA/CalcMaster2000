package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final TaskService taskService;

    public SubtaskController(SubtaskService subtaskService, TaskService taskService) {
        this.subtaskService = subtaskService;
        this.taskService = taskService;
    }

    // Viser en formular til at oprette en ny subtask
    @GetMapping("/subtask/addSubtask")
    public String showCreateSubtaskForm(@RequestParam("taskID") int taskID, Model model) {
        model.addAttribute("subtask", new Subtask());
        model.addAttribute("taskID", taskID);
        model.addAttribute("status", Status.values());
        model.addAttribute("priorities", Priority.values());
        return "addSubtask"; // Thymeleaf-skabelon til at oprette en subtask
    }

    // Håndterer oprettelsen af en ny subtask
    @PostMapping("/subtask/addSubtask")
    public String createSubtask(@ModelAttribute Subtask subtask) {
        subtaskService.createSubtask(subtask); // Gem task i databasen
        return "redirect:/tasks/" + subtask.getTaskID(); //
    }

    //Sletter en subtask
    @GetMapping("/subtasks/delete/{subtaskID}")
    public String deleteSubtask(@PathVariable int subtaskID, @RequestParam("taskID") int taskID) {
        subtaskService.deleteSubtaskById(subtaskID);
        return "redirect:/tasks/" + taskID; // Redirect tilbage til den tilhørende task
    }

    //opdatere en subtask
    @GetMapping("/subtasks/edit/{subtaskID}")
    public String showEditSubtaskForm(@PathVariable int subtaskID, Model model) {
        Subtask subtask = subtaskService.getSubtaskById(subtaskID); // Hent subtask baseret på ID
        model.addAttribute("subtask", subtask); // Tilføj subtask til modellen
        model.addAttribute("taskID", subtask.getTaskID()); // Tilføj taskID til modellen
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "editSubtask"; // Returner Thymeleaf-skabelonen
    }


    //Håndtere opdateringen af en subtask
    @PostMapping("/subtasks/edit")
    public String updateSubtask(@ModelAttribute Subtask subtask) {
        subtaskService.updateSubtask(subtask); // Opdater subtask i databasen
        return "redirect:/tasks/" + subtask.getTaskID();
    }

}
