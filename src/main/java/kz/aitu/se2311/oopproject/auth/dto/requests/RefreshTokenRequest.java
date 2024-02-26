package kz.aitu.se2311.oopproject.auth.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Refresh access token request")
public class RefreshTokenRequest {
    @Schema(description = "Refresh token from sign-in or sign-up", example = "eyJ0eXAiOiJSRUYiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W3siaWQiOjEsIm5hbWUiOiJVc2VyIiwiYXV0aG9yaXR5IjoiVXNlciJ9XSwiaWQiOjEsImVtYWlsIjoiZXhhbXBsZUBnbWFpbC5jb20iLCJzdWIiOiJleGFtcGxlIiwiaWF0IjoxNzA3MzI0NzI0LCJleHAiOjE3MDc5Mjk1MjR9.xEab8DMMqca9CCsqFfvDGGBTH31wlvWaWkvp1XAJf3w")
    @NotBlank(message = "Refresh token cannot be null")
    @NotNull
    @NotEmpty
    private String refreshToken;

}
