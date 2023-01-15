package ru.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.accidents.model.Accident;
import ru.accidents.service.AccidentService;
import ru.accidents.service.AccidentTypeService;

@Controller
public class AccidentControl {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;

    public AccidentControl(AccidentService accidentService, AccidentTypeService accidentTypeService) {
        this.accidentService = accidentService;
        this.accidentTypeService = accidentTypeService;
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return "redirect:/";
    }

    @GetMapping("/formUpdateAccident")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        var accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Ошибка 404 при получении данных инцидента.");
            return "404";
        }
        model.addAttribute("accident", accidentService.findById(id).get());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
