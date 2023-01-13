package ru.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
}