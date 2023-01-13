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

    public List<Accident> getAll() {
        return List.of();
    }

    public void create(Accident accident) {
    }

    public Accident getById(int id) {
        return new Accident();
    }

    public void update(Accident accident) {
    }
}