package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> getTokenByToken(String token);

    RefreshToken getRefreshTokenByUserId(Long id);
}
