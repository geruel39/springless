package springlesson.controller;

import springlesson.model.User;
import springlesson.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<User> getUsers() {
        return repository.findAll();
    }

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return repository.save(user);
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable long id) {
        return "User:" + repository.existsById(id);
    }
}