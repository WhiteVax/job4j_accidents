package ru.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AccidentRuleHibernate {
    private final CrudRepository crudRepository;

    public List<Rule> findAll() {
        return crudRepository.query("FROM Rule", Rule.class);
    }

    public List<Rule> findByIds(List<Integer> listIds) {
        List<Rule> rules = new ArrayList<>();
        for (var id: listIds) {
            var optional = crudRepository.optional("FROM Rule WHERE id = :fId", Rule.class, Map.of("fId", id));
            optional.ifPresent(rules :: add);
        }
        return rules;
    }
}
