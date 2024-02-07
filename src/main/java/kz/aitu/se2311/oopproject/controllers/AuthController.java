package kz.aitu.se2311.oopproject.controllers;

import kz.aitu.se2311.oopproject.requests.RegistrationRequest;
import kz.aitu.se2311.oopproject.responses.AuthenticationResponse;
import kz.aitu.se2311.oopproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(final @RequestBody RegistrationRequest request) {
        AuthenticationResponse response = userService.register(request);
        if (response.getMessage().equals("ok")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
