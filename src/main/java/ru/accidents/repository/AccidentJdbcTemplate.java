package ru.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;
import ru.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private static final String INSERT_ACCIDENT = "INSERT INTO accident (name, description, address, created, type_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_ACCIDENT = "SELECT * FROM accident ORDER BY ID";
    private static final String SELECT_GET_ID_ACCIDENT = "SELECT * FROM accident WHERE id = ?";
    private static final String UPDATE_ACCIDENT = "UPDATE accident SET name = ?, description =?, address = ?, type_id = ? WHERE id = ?";
    private static final String DELETE_ACCIDENT = "DELETE FROM accident WHERE id = ?";
    private static final String SELECT_BY_ID_ACCIDENT_TYPE = "SELECT * FROM accident_type WHERE id = ?";
    private static final String DELETE_BY_ACCIDENT_ID_ACCIDENTS_RULES = "DELETE FROM accidents_rules WHERE accident_id = ?";
    private static final String INSERT_ACCIDENTS_RULES = "INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)";

    private final JdbcTemplate jdbc;

    public Accident create(Accident accident) {
        jdbc.update(INSERT_ACCIDENT,
                accident.getName(),
                accident.getDescription(),
                accident.getAddress(),
                accident.getCreated(),
                accident.getType().getId());
        return accident;
    }

    public List<Accident> findAll() {
        return jdbc.query(SELECT_ALL_ACCIDENT,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setDescription(rs.getString("description"));
                    accident.setType(jdbc.queryForObject(
                            SELECT_BY_ID_ACCIDENT_TYPE, new BeanPropertyRowMapper<>(AccidentType.class), rs.getInt("type_id")));
                    accident.setCreated(rs.getTimestamp("created").toLocalDateTime());
                    accident.setAddress("address");
                    return accident;
                });
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(SELECT_GET_ID_ACCIDENT, new BeanPropertyRowMapper<>(Accident.class), id));
    }

    public void update(Accident accident) {
        deleteRulesAndAccidents(accident.getId());
        for (var rule: accident.getRules()) {
            insertRulesAndAccidents(accident.getId(), rule.getId());
        }
        jdbc.update(UPDATE_ACCIDENT,
                accident.getName(),
                accident.getDescription(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
    }

    public void delete(int id) {
        jdbc.update(DELETE_ACCIDENT, id);
    }

    public void insertRulesAndAccidents(int accidentId, int ruleId) {
        jdbc.update(INSERT_ACCIDENTS_RULES, accidentId, ruleId);
    }

    public void deleteRulesAndAccidents(int id) {
        jdbc.update(DELETE_BY_ACCIDENT_ID_ACCIDENTS_RULES, id);
    }
}
