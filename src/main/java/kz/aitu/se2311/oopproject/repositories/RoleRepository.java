package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT r.id, r.name FROM Role r WHERE r.name = ?1")
    Optional<Role> getRoleByName(String name);

}
