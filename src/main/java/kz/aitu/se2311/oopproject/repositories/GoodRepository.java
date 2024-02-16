package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Collection<Good> findByName(String name);
}
