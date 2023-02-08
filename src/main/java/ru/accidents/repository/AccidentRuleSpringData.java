package ru.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.accidents.model.Rule;

import java.util.List;

public interface AccidentRuleSpringData extends CrudRepository<Rule, Integer> {
    List<Rule> findAll();
}
