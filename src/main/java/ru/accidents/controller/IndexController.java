package ru.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.accidents.model.Accident;
import ru.accidents.service.AccidentService;

@Controller
public class IndexController {

    private final AccidentService accidentService;

    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("user", "Ivan Ivanov");
        return "index";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("user", "Ivan Ivanov");
        model.addAttribute("accidents", accidentService.getAll());
        return "index";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user", "Ivan");
        return "formSave";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return "redirect:/all";
    }

    @GetMapping("/formUpdate/{id}")
    public String viewUpdatePost(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", "Ivan");
        model.addAttribute("accident", accidentService.getById(id));
        return "update";
    }

    @PostMapping("/update")
    public String viewUpdatePost(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/all";
    }
}
