package kz.aitu.se2311.oopproject.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.aitu.se2311.oopproject.exceptions.UserAlreadyExists;
import lombok.Data;

@Data
@Schema(description = "Response for handling already used username or email")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAlreadyExistResponse {
    @Schema(description = "Username", example = "User with this username already exists")
    private String username;

    @Schema(description = "Email", example = "User with this email already exists")
    private String email;

    public UserAlreadyExistResponse(UserAlreadyExists e) {
        if (e.getUsername() != null)
            setUsername(e.getUsername());
        if (e.getEmail() != null)
            setEmail(e.getEmail());
    }
}
