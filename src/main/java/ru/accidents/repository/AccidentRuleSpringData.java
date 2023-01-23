package ru.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.accidents.model.Rule;

public interface AccidentRuleSpringData extends CrudRepository<Rule, Integer> {
}
