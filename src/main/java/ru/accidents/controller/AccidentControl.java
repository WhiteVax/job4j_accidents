package ru.accidents.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.accidents.model.Accident;
import ru.accidents.service.AccidentRuleService;
import ru.accidents.service.AccidentService;
import ru.accidents.service.AccidentTypeService;

import javax.servlet.http.HttpServletRequest;

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
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("accidents", accidentService.findAll());
        return "accident/index";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", accidentRuleService.findAll());
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                       Model model, HttpServletRequest req) {
        String[] rules = req.getParameterValues("rIds");
        if (rules == null) {
            model.addAttribute("message", "Выберите статьи!");
            return "accident/404";
        }
        accidentService.create(accident, rules, id);
        return "redirect:/";
    }

    @GetMapping("/formUpdateAccident")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        var accident = accidentService.findById(id);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (accident.isEmpty()) {
            model.addAttribute("message", "Ошибка 404 при получении данных инцидента.");
            return "accident/404";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", accidentRuleService.findAll());
        return "accident/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                         Model model, HttpServletRequest req) {
        String[] rule = req.getParameterValues("rIds");
        if (!accidentService.update(accident, rule, id)) {
            model.addAttribute("message", "Ошибка при обновлении инцидента.");
            return "accident/404";
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String update(@RequestParam ("id") int id) {
        accidentService.delete(id);
        return "redirect:/";
    }
}
