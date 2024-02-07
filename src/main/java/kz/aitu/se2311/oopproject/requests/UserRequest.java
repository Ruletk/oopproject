package kz.aitu.se2311.oopproject.requests;

import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String username;
    private String email;
}
