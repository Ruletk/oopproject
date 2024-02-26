package kz.aitu.se2311.oopproject.auth.services;

import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return createRole(role);
    }

    public Role createRole(Role role) throws DataIntegrityViolationException {
        return roleRepository.save(role);
    }

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }
}
