package kz.aitu.se2311.oopproject.services;

import io.jsonwebtoken.JwtException;
import kz.aitu.se2311.oopproject.auth.JwtService;
import kz.aitu.se2311.oopproject.entities.RefreshToken;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final JwtService jwtService;

    public RefreshToken createToken(User user) {
        String token = generateToken(user, JwtService.JwtType.REFRESH_TOKEN);
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .expirationDate(
                        Instant.ofEpochSecond(System.currentTimeMillis() +
                                JwtService.JwtType.REFRESH_TOKEN.getExpirationTimeout())
                )
                .user(user)
                .build();
        return save(refreshToken);
    }

    public RefreshToken updateToken(User user) {
        RefreshToken refreshToken = repository.getRefreshTokenByUserId(user.getId());
        if (refreshToken == null)
            return createToken(user);

        if (refreshToken.getExpirationDate().isBefore(Instant.now()))
            return refreshToken;

        refreshToken.setToken(generateToken(user, JwtService.JwtType.REFRESH_TOKEN));
        refreshToken.setExpirationDate(Instant.ofEpochSecond(System.currentTimeMillis() +
                JwtService.JwtType.REFRESH_TOKEN.getExpirationTimeout()));
        return save(refreshToken);
    }

    public String generateAccessToken(RefreshToken token) {
        User user = token.getUser();
        return generateAccessToken(token, user);
    }

    public String generateAccessToken(RefreshToken token, User user) {
        if (validateRefreshToken(token.getToken(), user))
            return generateToken(user, JwtService.JwtType.ACCESS_TOKEN);
        throw new JwtException("Invalid refresh token");
    }

    public boolean validateRefreshToken(String refreshToken, User user) {
        return jwtService.isTokenValid(refreshToken, user, JwtService.JwtType.REFRESH_TOKEN);
    }

    public RefreshToken getTokenByString(String token) {
        return repository.getTokenByToken(token).orElseThrow(() -> new JwtException(""));
    }

    private String generateToken(User user, JwtService.JwtType type) {
        return jwtService.generateToken(type, user);
    }

    private RefreshToken save(RefreshToken token) {
        return repository.save(token);
    }
}
