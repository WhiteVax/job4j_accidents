package ru.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
