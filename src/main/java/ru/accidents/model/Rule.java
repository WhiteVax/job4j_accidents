package ru.accidents.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {
    @EqualsAndHashCode.Include
    @NonNull
    private int id;

    private String name;
}
