package ru.accidents.repository;

import ru.accidents.model.Accident;
import org.springframework.data.repository.CrudRepository;

public interface AccidentSpringData extends CrudRepository<Accident, Integer> {
}
