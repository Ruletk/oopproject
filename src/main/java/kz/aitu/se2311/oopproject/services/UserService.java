package kz.aitu.se2311.oopproject.services;

import kz.aitu.se2311.oopproject.auth.JwtService;
import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.repositories.RoleRepository;
import kz.aitu.se2311.oopproject.repositories.UserRepository;
import kz.aitu.se2311.oopproject.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.requests.SignInRequest;
import kz.aitu.se2311.oopproject.responses.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private User save(User user) {
        log.debug(String.format("Saving user %s", user.getUsername()));
        return userRepository.save(user);
    }

    public User createUser(User user) {
        if (existUserByUsername(user.getUsername()))
            throw new RuntimeException("User with this username already exists");
        if (existUserByEmail(user.getEmail()))
            throw new RuntimeException("User with this email already exists");
        return save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"));
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void addNewRole(User user, Role role) {
        Collection<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        save(user);
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    @Deprecated
    public void getAdminRole() {
        addNewRole(getCurrentUser(), roleRepository.getRoleByName("Admin").orElseThrow());
    }
}
