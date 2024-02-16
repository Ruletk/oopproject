package kz.aitu.se2311.oopproject.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response with access and refresh tokens.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JwtResponse {
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String refreshToken;

    @Schema(description = "Access token")
    private String accessToken;

    @Schema(description = "Any errors")
    private String message;

    public JwtResponse(String refreshToken, String accessToken) {
        setRefreshToken(refreshToken);
        setAccessToken(accessToken);
    }
}
