package ru.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.accidents.model.Accident;
import ru.accidents.model.Rule;
import ru.accidents.repository.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentSpringData accidentMem;
    private final AccidentTypeSpringData accidentTypeMem;
    private final AccidentRuleSpringData accidentRuleMem;

    public List<Accident> findAll() {
        return (List<Accident>) accidentMem.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public void create(Accident accident, String[] ruleIds, int typeId) {
        accident.setType(accidentTypeMem.findById(typeId)
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("Not found AccidentType with this id = %s .", typeId))));
        List<Rule> rules = (List<Rule>) accidentRuleMem.findAllById(Arrays.stream(ruleIds).map(Integer::parseInt).toList());
        if (ruleIds.length != rules.size()) {
            throw new NoSuchElementException("Not all rule IDs found.");
        }
        accident.setRules(new HashSet<>(rules));
        accidentMem.save(accident);
    }

    public boolean update(Accident accident, String[] ruleIds, int typeId) {
        if (accidentMem.findById(accident.getId()).isEmpty()) {
            return false;
        }
        accident.setType(accidentTypeMem.findById(typeId)
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("Not found AccidentType with this id = %s .", typeId))));
        List<Rule> rules = (List<Rule>) accidentRuleMem.findAllById(Arrays.stream(ruleIds).map(Integer::parseInt).toList());
        if (ruleIds.length != rules.size()) {
            throw new NoSuchElementException("Not all rule IDs found.");
        }
        accident.setRules(new HashSet<>(rules));
        accidentMem.save(accident);
        return true;
    }

    public void delete(int id) {
        var accident = new Accident();
        accident.setId(id);
        accidentMem.delete(accident);
    }
}
