package kz.aitu.se2311.oopproject.services;

import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.repositories.RoleRepository;
import kz.aitu.se2311.oopproject.repositories.UserRepository;
import kz.aitu.se2311.oopproject.requests.AuthenticationRequest;
import kz.aitu.se2311.oopproject.requests.RegistrationRequest;
import kz.aitu.se2311.oopproject.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegistrationRequest request) throws DataIntegrityViolationException {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        Role userRole = roleRepository.getRoleByName("User").orElse(new Role());

        user.setRoles(Collections.singletonList(userRole));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        try {
            User registeredUser = createUser(user);
        } catch (DataIntegrityViolationException e) {
            return AuthenticationResponse.builder().message(e.getMessage()).build();
        }

        String token = "Implement token creation here";

        return AuthenticationResponse.builder().message("ok").token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws NoSuchElementException, AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        User user = getUserByUsername(request.getUsername()).orElseThrow();

        String token = "Implement token in auth here";

        return AuthenticationResponse.builder().message("ok").token(token).build();
    }

    public User createUser(String username, String email) {
        User user = User.builder()
                .username(username)
                .email(email)
                .build();
        return createUser(user);
    }


    public User createUser(User user) throws DataIntegrityViolationException {
        checkUserCredentials(user);

        return userRepository.save(user);
    }

    /**
     * Checking username and email on availability.
     *
     * @param user User class object. User must have username and email.
     * @throws DataIntegrityViolationException Spring data JPA exception on unique constrain.
     */
    private void checkUserCredentials(User user) throws DataIntegrityViolationException {
        if (user.getUsername() == null || user.getEmail() == null)
            throw new IllegalArgumentException("Username and email must be provided!");

        if (getUserByUsername(user.getUsername()).isPresent())
            throw new DataIntegrityViolationException("User with username " + user.getUsername() + " already exists.");
        if (getUserByEmail(user.getEmail()).isPresent())
            throw new DataIntegrityViolationException("User with email " + user.getEmail() + "already exists.");
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}
