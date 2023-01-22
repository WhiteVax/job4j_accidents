package ru.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "accident")
public class Accident {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String address;
    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccidentType type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accidents_rules",
            joinColumns = @JoinColumn(name = "accident_type"),
            inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private Set<Rule> rules;
}
