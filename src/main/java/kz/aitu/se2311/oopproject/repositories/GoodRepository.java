package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {
}
