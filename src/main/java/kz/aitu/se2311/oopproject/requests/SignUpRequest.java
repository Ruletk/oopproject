package kz.aitu.se2311.oopproject.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Registration request")
@AllArgsConstructor
public class SignUpRequest {
    @Schema(description = "Username", example = "johnsmith")
    @Size(min = 3, max = 64, message = "Username must be between 3 and 64 characters long")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "Email address", example = "john.smith@gmail.com")
    @Size(min = 5, max = 255, message = "Email address must be between 5 and 128 characters long")
    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Email address must be in the format user@example.com")
    private String email;

    @Schema(description = "Password", example = "VeRy_sEcReT_PaSsWoRd")
    @Size(max = 255, message = "Password must be less than 255 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
