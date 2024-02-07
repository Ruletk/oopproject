package kz.aitu.se2311.oopproject.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.aitu.se2311.oopproject.requests.SignInRequest;
import kz.aitu.se2311.oopproject.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.responses.jwttokens.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "User registration")
    public JwtAuthenticationResponse signUp(final @RequestBody @Valid SignUpRequest request) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "User authentication")
    public JwtAuthenticationResponse signIn(final @RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }
}
