package kz.aitu.se2311.oopproject.responses.jwttokens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response for expired jwt token")
public class ExpiredJwtAuthenticationResponse {
    @Schema(description = "Error message", example = "JWT Token was expired")
    private String message;
}