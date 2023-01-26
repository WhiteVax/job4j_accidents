package ru.accidents.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.accidents.model.User;
import ru.accidents.service.AuthorityService;
import ru.accidents.service.UserService;

@Controller
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityService authorities;

    public RegControl(PasswordEncoder encoder, UserService users, AuthorityService authorities) {
        this.encoder = encoder;
        this.users = users;
        this.authorities = authorities;
    }

    @PostMapping("/registrationUser")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthorityUser());
        if (!users.save(user)) {
            return "redirect:/login?fail=true";
        }
        return "redirect:/login?registration=true";
    }

    @GetMapping("/registration")
    public String regPage() {
        return "user/registration";
    }
}