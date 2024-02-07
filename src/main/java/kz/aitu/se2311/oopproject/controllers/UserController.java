package kz.aitu.se2311.oopproject.controllers;

import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.exceptions.UserAlreadyExists;
import kz.aitu.se2311.oopproject.requests.UserRequest;
import kz.aitu.se2311.oopproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(final @PathVariable String username) {
        User user = userService.getUserByUsername(username).orElse(null);
        if (user == null) {
            Map<String, String> map = new HashMap<>();
            map.put("error", "User with username " + username + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        return ResponseEntity.ok(user);
    }
}
