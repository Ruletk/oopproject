package kz.aitu.se2311.oopproject.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${spring.security.secret_token}")
    private String SECRET_KEY;

    @Getter
    public enum JwtType {
        REFRESH_TOKEN("REF", 1000 * 60 * 60 * 24 * 7), // 7 days
        ACCESS_TOKEN("JWT", 1000 * 60 * 10); // 10 minutes
        private final String typ;
        private final int expirationTimeout;

        JwtType(String typ, int timeout) {
            this.typ = typ;
            this.expirationTimeout = timeout;
        }
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(JwtType type, UserDetails userDetails) {
        return generateToken(type, new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails, JwtType type) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) &&
                !isTokenExpired(token) &&
                getHeaders(token).get("typ").equals(type.getTyp());
    }

    private JwsHeader<?> getHeaders(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getHeader();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String generateToken(JwtType type, Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setHeaderParam("typ", type.getTyp())
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + type.getExpirationTimeout()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
