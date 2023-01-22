package ru.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.accidents.model.AccidentType;
import ru.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {
    private static final String SELECT_ALL_RULES = "SELECT * FROM accident_type";
    private static final String FIND_BY_ID = "SELECT * FROM accident_type WHERE id = ?";

    private final JdbcTemplate jdbc;

    public List<AccidentType> findAll() {
        return jdbc.query(SELECT_ALL_RULES,
                (rs, row) -> {
                    var type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public Optional<AccidentType> findById(int typeId) {
        return Optional.of(Objects.requireNonNull(jdbc.queryForObject(
                FIND_BY_ID, new BeanPropertyRowMapper<>(AccidentType.class), typeId)));
    }
}
