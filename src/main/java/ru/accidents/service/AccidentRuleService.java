package ru.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.accidents.model.Rule;
import ru.accidents.repository.AccidentRuleHibernate;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class AccidentRuleService {
    private final AccidentRuleHibernate accidentRuleMem;

    public List<Rule> findAll() {
        return accidentRuleMem.findAll();
    }

    public List<Rule> findByIds(String[] stringIds) {
        List<Integer> listIds = Arrays.stream(stringIds).map(Integer::parseInt).toList();
        return accidentRuleMem.findByIds(listIds);
    }
}
