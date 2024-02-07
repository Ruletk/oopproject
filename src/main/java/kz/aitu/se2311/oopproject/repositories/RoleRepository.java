package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByName(String name);

}
