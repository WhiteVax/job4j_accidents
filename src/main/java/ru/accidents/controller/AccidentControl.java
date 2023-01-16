package ru.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.accidents.model.Accident;
import ru.accidents.model.Rule;
import ru.accidents.service.AccidentRuleService;
import ru.accidents.service.AccidentService;
import ru.accidents.service.AccidentTypeService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

@Controller
public class AccidentControl {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final AccidentRuleService accidentRuleService;

    public AccidentControl(AccidentService accidentService, AccidentTypeService accidentTypeService, AccidentRuleService accidentRuleService) {
        this.accidentService = accidentService;
        this.accidentTypeService = accidentTypeService;
        this.accidentRuleService = accidentRuleService;
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", accidentRuleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                       Model model, HttpServletRequest req) {
        String[] rules = req.getParameterValues("rIds");
        if (rules == null) {
            model.addAttribute("message", "Выберите статьи!");
            return "404";
        }
        accidentService.create(accident, rules, id);
        return "redirect:/";
    }

    @GetMapping("/formUpdateAccident")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        var accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Ошибка 404 при получении данных инцидента.");
            return "404";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("rules", accidentRuleService.findAll());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                         Model model, HttpServletRequest req) {
        String[] rule = req.getParameterValues("rIds");
        if (rule == null) {
            model.addAttribute("message", "Выберите статьи!");
            return "404";
        }
        if (!accidentService.update(accident, rule, id)) {
            model.addAttribute("message", "Ошибка при обновлении инцидента.");
            return "404";
        }
        return "redirect:/";
    }
}
