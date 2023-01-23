package ru.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;
import ru.accidents.model.AccidentType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private static final String INSERT_ACCIDENT = "INSERT INTO accident (name, description, address, created, type_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_ACCIDENT =
                    """
                    SELECT a.id, a.name, a.description, a.address, a.type_id, tp.name type, a.created 
                    FROM accident a 
                    JOIN accident_type tp ON a.type_id = tp.id 
                    ORDER BY a.id
                    """;

    private static final String SELECT_GET_ID_ACCIDENT = "SELECT * FROM accident WHERE id = ?";
    private static final String UPDATE_ACCIDENT = "UPDATE accident SET name = ?, description =?, address = ?, type_id = ? WHERE id = ?";
    private static final String DELETE_ACCIDENT = "DELETE FROM accident WHERE id = ?";
    private static final String DELETE_BY_ACCIDENT_ID_ACCIDENTS_RULES = "DELETE FROM accidents_rules WHERE accident_id = ?";
    private static final String INSERT_ACCIDENTS_RULES = "INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)";

    private final JdbcTemplate jdbc;

    public Accident create(Accident accident) {
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            var ps = connection.prepareStatement(INSERT_ACCIDENT, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getDescription());
            ps.setString(3, accident.getAddress());
            ps.setTimestamp(4, Timestamp.valueOf(accident.getCreated()));
            ps.setInt(5, accident.getType().getId());
            return ps;
        }, keyHolder);
        for (var rule : accident.getRules()) {
            insertRulesAndAccidents(keyHolder.getKey().intValue(), rule.getId());
        }
        return accident;
    }

    public List<Accident> findAll() {
        return jdbc.query(SELECT_ALL_ACCIDENT,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setDescription(rs.getString("description"));
                    accident.setType(typeRowMapper.mapRow(rs, row));
                    accident.setCreated(rs.getTimestamp("created").toLocalDateTime());
                    accident.setAddress(rs.getString("address"));
                    return accident;
                });
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(SELECT_GET_ID_ACCIDENT, new BeanPropertyRowMapper<>(Accident.class), id));
    }

    public void update(Accident accident) {
        deleteRulesAndAccidents(accident.getId());
        jdbc.update(UPDATE_ACCIDENT,
                accident.getName(),
                accident.getDescription(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        for (var rule : accident.getRules()) {
            insertRulesAndAccidents(accident.getId(), rule.getId());
        }
    }

    public void delete(int id) {
        deleteRulesAndAccidents(id);
        jdbc.update(DELETE_ACCIDENT, id);
    }

    public void insertRulesAndAccidents(int accidentId, int ruleId) {
        jdbc.update(INSERT_ACCIDENTS_RULES, accidentId, ruleId);
    }

    public void deleteRulesAndAccidents(int id) {
        jdbc.update(DELETE_BY_ACCIDENT_ID_ACCIDENTS_RULES, id);
    }

    private final RowMapper<AccidentType> typeRowMapper = (resultSet, rowNum) -> {
        var type = new AccidentType();
        type.setId(resultSet.getInt("type_id"));
        type.setName(resultSet.getString("type"));
        return type;
    };
}
