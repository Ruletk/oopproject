package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
