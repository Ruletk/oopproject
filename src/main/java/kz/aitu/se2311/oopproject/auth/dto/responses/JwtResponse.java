package kz.aitu.se2311.oopproject.auth.dto.responses;

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
    @Schema(
            description = "Refresh token for receiving access token by request",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )
    private String refreshToken;

    @Schema(
            description = "Access token to authenticate and performing actions",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA4ODc2MTQxLCJleHAiOjE3MDg4NzY3NDF9.hO4nwaee6aTZgEYvKCuzRsKtnka7125ZC83ncyCdRB8"
    )
    private String accessToken;

    @Schema(description = "Any errors or notifications", example = "Jwt token was expired")
    private String message;

    public JwtResponse(String refreshToken, String accessToken) {
        setRefreshToken(refreshToken);
        setAccessToken(accessToken);
    }
}
