package kz.aitu.se2311.oopproject.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.aitu.se2311.oopproject.requests.RefreshTokenRequest;
import kz.aitu.se2311.oopproject.requests.SignInRequest;
import kz.aitu.se2311.oopproject.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.responses.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "User registration")
    public ResponseEntity<JwtResponse> signUp(
            final @Valid @RequestBody SignUpRequest request,
            final BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JwtResponse(null, null, "Invalid JSON structure"));
        }
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "User authentication")
    public JwtResponse signIn(final @RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token using refresh token")
    public JwtResponse refreshToken(final @RequestBody @Valid RefreshTokenRequest request) {
        return authService.refreshAccessToken(request);
    }
}
