package ru.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.accidents.model.AccidentType;
import ru.accidents.repository.AccidentTypeHibernate;
import ru.accidents.repository.AccidentTypeJdbcTemplate;
import ru.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeHibernate accidentTypeMem;

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeMem.findById(id);
    }
}