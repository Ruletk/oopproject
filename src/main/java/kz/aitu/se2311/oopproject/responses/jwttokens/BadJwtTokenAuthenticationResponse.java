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
@Schema(description = "Response for bad or wrong jwt token")
public class BadJwtTokenAuthenticationResponse {

    @Schema(description = "Error message", example = "Invalid JWT Token")
    private String message;
}
