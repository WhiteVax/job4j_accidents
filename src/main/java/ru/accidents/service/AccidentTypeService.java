package ru.accidents.service;

import org.springframework.stereotype.Service;
import ru.accidents.model.AccidentType;
import ru.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeService {
    private final AccidentTypeMem accidentTypeMem;

    public AccidentTypeService(AccidentTypeMem accidentTypeMem) {
        this.accidentTypeMem = accidentTypeMem;
    }

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeMem.findById(id);
    }
}