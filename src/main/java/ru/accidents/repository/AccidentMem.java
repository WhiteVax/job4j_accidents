package ru.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    public Optional<Accident> findById(int id) {
        return Optional.of(accidents.get(id));
    }

    public void create(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public void update(Accident accident) {
        accidents.replace(accident.getId(), accident);
    }
}
