package kz.aitu.se2311.oopproject.controllers;

import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.exceptions.UserAlreadyExists;
import kz.aitu.se2311.oopproject.requests.UserRequest;
import kz.aitu.se2311.oopproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(final @PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/get-admin-role")
    public void getAdmin() {
        userService.getAdminRole();
    }
}
