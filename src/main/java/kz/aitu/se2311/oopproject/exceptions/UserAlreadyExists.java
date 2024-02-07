package kz.aitu.se2311.oopproject.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAlreadyExists extends RuntimeException {
    private String username;
    private String email;

    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists(String message, String username, String email) {
        super(message);
        setUsername(username);
        setEmail(email);
    }
}
