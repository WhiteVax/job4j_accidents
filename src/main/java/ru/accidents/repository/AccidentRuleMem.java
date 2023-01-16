package ru.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;
import ru.accidents.model.AccidentType;
import ru.accidents.model.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentRuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    {
        rules.put(1, new Rule(1, "Статья 1.1 А"));
        rules.put(2, new Rule(2, "Статья 1.1 Б"));
        rules.put(3, new Rule(3, "Статья 1.1 В"));
    }

    public List<Rule> findAll() {
        return rules.values().stream().toList();
    }

    public List<Rule> findByIds(List<Integer> listIds) {
        return listIds.stream().filter(id -> rules.containsKey(id))
                .map(Rule :: new)
                .toList();
    }
}
