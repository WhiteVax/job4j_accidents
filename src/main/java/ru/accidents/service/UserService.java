package ru.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.accidents.model.User;
import ru.accidents.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public boolean save(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error(String.format("This user already exists with username = %s.", user.getUsername()));
            return false;
        }
        return true;
    }
}
