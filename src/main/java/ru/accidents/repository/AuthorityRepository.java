package ru.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.accidents.model.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    Authority findByAuthority(String authority);
}
