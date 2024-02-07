package kz.aitu.se2311.oopproject.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin panel")
public class AdminController {
    private final UserService userService;
    @GetMapping("/all-users")
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
