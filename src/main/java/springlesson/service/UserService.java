package springlesson.service;

import springlesson.exception.ResourceNotFoundException;
import springlesson.model.User;
import springlesson.repository.UserRepository;
import springlesson.dto.UserRequest;
import springlesson.dto.UserResponse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserResponse> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponse registerUser(UserRequest request) {

        User user = new User(
                request.getName(),
                request.getEmail()
        );

        User savedUser = repository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public UserResponse getUserById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID " + id + " not found"
                        )
                );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponse updateUser(Long id, UserRequest request) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID " + id + " not found"
                        )
                );

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updatedUser = repository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );
    }

    public UserResponse patchUser(Long id, Map<String, Object> updates) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID " + id + " not found"
                        )
                );

        if (updates.containsKey("name")) {
            user.setName((String) updates.get("name"));
        }

        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }

        User updatedUser = repository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );
    }

    public void deleteUser(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID " + id + " not found"
                        )
                );

        repository.delete(user);
    }

}