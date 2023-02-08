package ru.accidents.repository;

import ru.accidents.model.Accident;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccidentSpringData extends CrudRepository<Accident, Integer> {
    List<Accident> findAll();
}
