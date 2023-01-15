package ru.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> list = new ConcurrentHashMap<>();

    {
        list.put(1, new AccidentType(1, "Две машины"));
        list.put(2, new AccidentType(2, "Машина и человек"));
        list.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    public List<AccidentType> findAll() {
        return list.values().stream().toList();
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.of(list.get(id));
    }
}