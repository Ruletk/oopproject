package kz.aitu.se2311.oopproject.auth.services;

import jakarta.transaction.Transactional;
import kz.aitu.se2311.oopproject.auth.dto.requests.RefreshTokenRequest;
import kz.aitu.se2311.oopproject.auth.dto.requests.SignInRequest;
import kz.aitu.se2311.oopproject.auth.dto.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.auth.dto.responses.JwtResponse;
import kz.aitu.se2311.oopproject.entities.RefreshToken;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.NoSuchElementException;

@Service("authService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public JwtResponse signUp(SignUpRequest request) {
        log.debug(String.format("Registering user %s", request.getUsername()));
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(roleRepository.getRoleByName("ROLE_USER").orElseThrow(
                        () -> new NoSuchElementException("Programmer, you forgot about roles in initializers.RoleInitializer")
                )))
                .build();

        user = userService.createUser(user);

        RefreshToken refreshToken = refreshTokenService.createToken(user);
        String accessToken = refreshTokenService.generateAccessToken(refreshToken, user);

        return new JwtResponse(refreshToken.getToken(), accessToken);
    }

    public JwtResponse signIn(SignInRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException e) {
            log.info(e.getMessage());
            throw e;
        }
        User user = userService.getUserByUsername(request.getUsername());

        RefreshToken refreshToken = refreshTokenService.updateToken(user);
        String accessToken = refreshTokenService.generateAccessToken(refreshToken, user);

        return new JwtResponse(refreshToken.getToken(), accessToken);
    }

    public JwtResponse refreshAccessToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.getTokenByString(request.getRefreshToken());
        String accessToken = refreshTokenService.generateAccessToken(refreshToken);

        return new JwtResponse(refreshToken.getToken(), accessToken);
    }
}
