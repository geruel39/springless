package springlesson.controller;

import springlesson.dto.UserRequest;
import springlesson.dto.UserResponse;
import springlesson.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponse registerUser(@Valid @RequestBody UserRequest request) {
        return userService.registerUser(request);
    }
}