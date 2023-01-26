package ru.accidents.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginControl {
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registration", required = false) String registration,
                            @RequestParam(value = "fail", required = false) String fail,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Логин или пароль неверный !";
        }
        if (logout != null) {
            errorMessage = "Вы успешно вышли из системы !";
        }
        if (registration != null) {
            errorMessage = "Вы успешно зарегистрировались !";
        }
        if (fail != null) {
            errorMessage = "Данный логин уже зарегистрирован !";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}