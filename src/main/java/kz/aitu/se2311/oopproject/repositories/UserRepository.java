package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
