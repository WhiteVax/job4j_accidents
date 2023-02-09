package ru.accidents.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.accidents.model.Accident;
import ru.accidents.service.AccidentRuleService;
import ru.accidents.service.AccidentService;
import ru.accidents.service.AccidentTypeService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("accidents")
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
    public String viewFindAll(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("accidents", accidentService.findAll());
        return "accident/index";
    }

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", accidentRuleService.findAll());
        return "accident/createAccident";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                       Model model, HttpServletRequest req) {
        String[] rules = req.getParameterValues("rIds");
        if (rules == null) {
            model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            model.addAttribute("message", "Выберите статьи!");
            return "accident/404";
        }
        accidentService.create(accident, rules, id);
        return "redirect:/";
    }

    @GetMapping("/formUpdate")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        var accident = accidentService.findById(id);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (accident.isEmpty()) {
            model.addAttribute("message", String.format("Ошибка 404 при получении данных инцидента с id = %s.", id));
            return "accident/404";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", accidentRuleService.findAll());
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                         Model model, HttpServletRequest req) {
        String[] rule = req.getParameterValues("rIds");
        if (!accidentService.update(accident, rule, id)) {
            model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            model.addAttribute("message", String.format("Ошибка при обновлении инцидента id = %s.", accident.getId()));
            return "accident/404";
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam ("id") int id) {
        accidentService.delete(id);
        return "redirect:/";
    }
}
