package ru.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.accidents.model.AccidentType;

public interface AccidentTypeSpringData extends CrudRepository<AccidentType, Integer> {
}
