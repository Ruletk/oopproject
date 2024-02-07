package kz.aitu.se2311.oopproject.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import kz.aitu.se2311.oopproject.exceptions.UserAlreadyExists;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAlreadyExistResponse {
    private String username;
    private String email;

    public UserAlreadyExistResponse(UserAlreadyExists e) {
        if (e.getUsername() != null)
            setUsername(e.getUsername());
        if (e.getEmail() != null)
            setEmail(e.getEmail());
    }
}
