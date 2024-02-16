package kz.aitu.se2311.oopproject.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import kz.aitu.se2311.oopproject.exceptions.UserAlreadyExists;
import kz.aitu.se2311.oopproject.responses.JwtResponse;
import kz.aitu.se2311.oopproject.responses.UserAlreadyExistResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
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
}
