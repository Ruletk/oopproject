package kz.aitu.se2311.oopproject.auth;

import kz.aitu.se2311.oopproject.entities.RefreshToken;
import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.requests.RefreshTokenRequest;
import kz.aitu.se2311.oopproject.requests.SignInRequest;
import kz.aitu.se2311.oopproject.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.responses.jwttokens.JwtAuthenticationResponse;
import kz.aitu.se2311.oopproject.services.RefreshTokenService;
import kz.aitu.se2311.oopproject.services.RoleService;
import kz.aitu.se2311.oopproject.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.debug(String.format("Registering user %s", request.getUsername()));
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(roleRepository.getRoleByName("User").orElse(new Role())))
                .build();

        user = userService.createUser(user);

        RefreshToken refreshToken = refreshTokenService.createToken(user);
        String accessToken = refreshTokenService.generateAccessToken(refreshToken, user);

        return new JwtAuthenticationResponse(refreshToken.getToken(), accessToken);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        User user = userService.getUserByUsername(request.getUsername());

        RefreshToken refreshToken = refreshTokenService.updateToken(user);
        String accessToken = refreshTokenService.generateAccessToken(refreshToken, user);

        return new JwtAuthenticationResponse(refreshToken.getToken(), accessToken);
    }

    public JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.getTokenByString(request.getRefreshToken());
        String accessToken = refreshTokenService.generateAccessToken(refreshToken);

        return new JwtAuthenticationResponse(refreshToken.getToken(), accessToken);
    }
}
