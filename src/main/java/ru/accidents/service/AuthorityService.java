package ru.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.accidents.model.Authority;
import ru.accidents.repository.AuthorityRepository;

@Service
@AllArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityService;

    public Authority findByAuthorityUser() {
        return authorityService.findByAuthority("ROLE_USER");
    }
}