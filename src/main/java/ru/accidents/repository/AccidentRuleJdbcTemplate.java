package ru.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.accidents.model.Accident;
import ru.accidents.model.AccidentType;
import ru.accidents.model.Rule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentRuleJdbcTemplate {

    private static final String SELECT_ALL_RULES = "SELECT * FROM auto_rules";
    private static final String FIND_BY_IDS = "SELECT * FROM auto_rules WHERE id = ?";

    private final JdbcTemplate jdbc;

    public List<Rule> findByIds(List<Integer> toList) {
        List<Rule> rules = new ArrayList<>();
        for (var id : toList) {
            var item = Optional.of(Objects.requireNonNull(jdbc.queryForObject(
                    FIND_BY_IDS, new BeanPropertyRowMapper<>(Rule.class), id)));
            item.ifPresent(rules::add);
        }
        return rules;
    }

    public List<Rule> findAll() {
        return jdbc.query(SELECT_ALL_RULES,
                (rs, row) -> {
                    var rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}
