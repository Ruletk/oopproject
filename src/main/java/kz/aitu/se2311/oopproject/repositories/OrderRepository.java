package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
