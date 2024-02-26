package kz.aitu.se2311.oopproject.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.aitu.se2311.oopproject.auth.dto.responses.JwtResponse;
import kz.aitu.se2311.oopproject.users.dto.responses.UserAlreadyExistResponse;
import kz.aitu.se2311.oopproject.users.exceptions.UserAlreadyExists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
@Tag(name = "Exception handler controller")
//@Hidden
public class ExceptionController {

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JwtResponse expiredToken(ExpiredJwtException ignore) {
        return new JwtResponse(null, null, "Your JWT token was expired");
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JwtResponse badToken(JwtException ignore) {
        return new JwtResponse(null, null, "Invalid JWT Token");
    }

    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public UserAlreadyExistResponse userAlreadyExist(UserAlreadyExists e) {
        return new UserAlreadyExistResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> badJson(MethodArgumentNotValidException ignore) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "JSON validation error. Check the correctness of the JSON"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JwtResponse badCredentials(BadCredentialsException ignore) {
        return new JwtResponse(null, null, "Wrong username or password");
    }
}
