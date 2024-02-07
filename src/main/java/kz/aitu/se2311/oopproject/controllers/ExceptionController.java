package kz.aitu.se2311.oopproject.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import kz.aitu.se2311.oopproject.exceptions.UserAlreadyExists;
import kz.aitu.se2311.oopproject.responses.UserAlreadyExistResponse;
import kz.aitu.se2311.oopproject.responses.jwttokens.BadJwtTokenAuthenticationResponse;
import kz.aitu.se2311.oopproject.responses.jwttokens.ExpiredJwtAuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExpiredJwtAuthenticationResponse expiredToken(ExpiredJwtException e) {
        return new ExpiredJwtAuthenticationResponse(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BadJwtTokenAuthenticationResponse badToken(JwtException ignoredE) {
        return new BadJwtTokenAuthenticationResponse("Invalid JWT Token");
    }

    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public UserAlreadyExistResponse userAlreadyExist(UserAlreadyExists e) {
        return new UserAlreadyExistResponse(e);
    }
}
