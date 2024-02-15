package kz.aitu.se2311.oopproject.initializers;

import jakarta.annotation.PostConstruct;
import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleInitializer {
    private final RoleRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            Role userRole = Role.builder().name("ROLE_USER").build();
            Role adminRole = Role.builder().name("ROLE_ADMIN").build();
            repository.saveAll(Arrays.asList(userRole, adminRole));
        }
    }
}
