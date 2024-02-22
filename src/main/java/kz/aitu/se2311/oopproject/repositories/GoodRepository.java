package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Optional<Good> findByName(String name);
    Optional<Good> findBySlug(String slug);
}
