package kz.aitu.se2311.oopproject.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.aitu.se2311.oopproject.exceptions.JsonParseException;
import kz.aitu.se2311.oopproject.requests.RefreshTokenRequest;
import kz.aitu.se2311.oopproject.requests.SignInRequest;
import kz.aitu.se2311.oopproject.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.responses.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ) throws JsonParseException {
        if (result.hasErrors()) {
            throw new JsonParseException(result.getAllErrors());
        }

        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "User authentication")
    public ResponseEntity<JwtResponse> signIn(final @RequestBody @Valid SignInRequest request, final BindingResult result) {
        if (result.hasErrors())
            throw new JsonParseException(result.getAllErrors());
        JwtResponse response;
        try {
            response = authService.signIn(request);
        } catch (BadCredentialsException ignore) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new JwtResponse(null, null, "Wrong username or password")
            );
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token using refresh token")
    public JwtResponse refreshToken(final @RequestBody @Valid RefreshTokenRequest request) {
        return authService.refreshAccessToken(request);
    }
}
