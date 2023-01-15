package ru.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.accidents.model.Accident;
import ru.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;

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

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }
}
