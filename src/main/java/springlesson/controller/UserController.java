package springlesson.controller;

import org.springframework.http.ResponseEntity;
import springlesson.dto.UserRequest;
import springlesson.dto.UserResponse;
import springlesson.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {

        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}")
    public UserResponse patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {

        return userService.patchUser(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }


}