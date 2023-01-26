package ru.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.accidents.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}