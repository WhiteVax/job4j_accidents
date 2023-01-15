package ru.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.accidents.model.Accident;
import ru.accidents.repository.AccidentMem;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public void create(Accident accident) {
    }

    public void update(Accident accident) {
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }
}
