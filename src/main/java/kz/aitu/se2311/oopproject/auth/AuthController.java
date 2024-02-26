package kz.aitu.se2311.oopproject.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.aitu.se2311.oopproject.auth.dto.requests.RefreshTokenRequest;
import kz.aitu.se2311.oopproject.auth.dto.requests.SignInRequest;
import kz.aitu.se2311.oopproject.auth.dto.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.auth.dto.responses.JwtResponse;
import kz.aitu.se2311.oopproject.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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


    @Operation(
            summary = "User registration",
            responses = {
                    @ApiResponse(responseCode = "200", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                        "refreshToken": "eyJ0eXAiOiJSRUYiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTcwODk0MzE2NCwiZXhwIjoxNzA5NTQ3OTY0fQ.4avVEE4U4B3g1nAz9exvULDFeLfGX_tz9KfcUJPr4AE",
                                        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTcwODk0MzE2NCwiZXhwIjoxNzA4OTQzNzY0fQ.XXCUlXoXxBk7XLkNAlHiRAmK1Pj-HF4whGi6jBMZLbo"
                                    }
                                    """)
                    )}),
                    @ApiResponse(responseCode = "400", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                      "message": "JSON validation error. Check the correctness of the JSON"
                                    }
                                    """)
                    )}),
                    @ApiResponse(responseCode = "409", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                        "username": "User with this username already exists",
                                        "email": "User with this email already exists"
                                    }
                                    """)
                    )})
            })
    @PostMapping("/sign-up")
    public ResponseEntity<JwtResponse> signUp(
            final @RequestBody @Valid SignUpRequest request
    ) {
        JwtResponse response = authService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "User authentication",
            responses = {
                    @ApiResponse(responseCode = "200", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                        "refreshToken": "eyJ0eXAiOiJSRUYiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA4OTQyOTk1LCJleHAiOjE3MDk1NDc3OTV9.Mq3XgaFyPHBHlwcbevz02K6f4BlWjzfJjPl9KUGBcCQ",
                                        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA4OTQyOTk2LCJleHAiOjE3MDg5NDM1OTZ9.9T5aGfgKw5o39cko0Fko3EMBC83eVQ-fOGTYZiH-x8o"
                                    }
                                    """)
                    )}),
                    @ApiResponse(responseCode = "400", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                      "message": "JSON validation error. Check the correctness of the JSON"
                                    }
                                    """)
                    )}),
                    @ApiResponse(responseCode = "401", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                      "message": "Wrong username or password"
                                    }
                                    """)
                    )})
            })
    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(final @RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/refresh-token")
    @Operation(
            summary = "Refresh access token using refresh token",
            description = "This method allows you to update the access token using an update token. " +
                    "If the update token has expired or is invalid, the status 401 Unauthorized will be returned. " +
                    "If the access token has been successfully updated, a new access token and the current update token will be returned.",
            responses = {
                    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                            example = """
                                    {
                                        "refreshToken": "eyJ0eXAiOiJSRUYiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA4OTQ1NzQ3LCJleHAiOjE3MDk1NTA1NDd9.Ux6j9CilzJTJJrdSD-yh1RBt13TWcepvcNdmvBh07PI",
                                        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA4OTQ1NzkxLCJleHAiOjE3MDg5NDYzOTF9.v6xxE1hMTQfRUTDnOUYX3nPgxz6FJJje9pyqc_CRLa0"
                                    }
                                    """
                    ))}),
                    @ApiResponse(responseCode = "400", content = {@Content(
                            schema = @Schema(example = """
                                    {
                                      "message": "JSON validation error. Check the correctness of the JSON"
                                    }
                                    """)
                    )}),
                    @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(
                            example = """
                                    {
                                        "message": "Invalid JWT Token"
                                    }
                                    """
                    ))})
            })
    public JwtResponse refreshToken(final @RequestBody @Valid RefreshTokenRequest request) {
        return authService.refreshAccessToken(request);
    }
}
