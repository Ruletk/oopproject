package kz.aitu.se2311.oopproject.services;

import ch.qos.logback.classic.encoder.JsonEncoder;
import kz.aitu.se2311.oopproject.auth.JwtService;
import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.requests.SignInRequest;
import kz.aitu.se2311.oopproject.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.responses.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Authenticator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.debug(String.format("Registering user %s", request.getUsername()));
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(roleRepository.getRoleByName("User").orElse(new Role())))
                .build();

        user = userService.createUser(user);

        String token = jwtService.generateToken(user, JwtService.refreshTokenExpirationTimeout);

        return new JwtAuthenticationResponse(token);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        User user = userService.getUserByUsername(request.getUsername());

        String token = jwtService.generateToken(user, JwtService.refreshTokenExpirationTimeout);

        return new JwtAuthenticationResponse(token);
    }
}
