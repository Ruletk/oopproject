package kz.aitu.se2311.oopproject.requests;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
