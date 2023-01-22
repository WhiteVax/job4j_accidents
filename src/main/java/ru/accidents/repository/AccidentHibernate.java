package ru.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final CrudRepository crudRepository;

    public List<Accident> findAll() {
        return crudRepository.query("FROM Accident", Accident.class);
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "FROM Accident a JOIN FETCH a.rules WHERE a.id = :fId",
                Accident.class, Map.of("fId", id));
    }

    public void create(Accident accident) {
        crudRepository.run(session -> session.save(accident));
    }

    public void update(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
    }

    public void delete(int id) {
        crudRepository.run("DELETE FROM Accident WHERE id = :fId", Map.of("fId", id));
    }
}
