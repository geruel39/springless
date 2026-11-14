package springlesson.service;



import springlesson.exception.ResourceNotFoundException;
import springlesson.model.User;
import springlesson.repository.UserRepository;
import springlesson.dto.UserRequest;
import springlesson.dto.UserResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


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

        if (updates.isEmpty()) {
            throw new IllegalArgumentException("No fields provided");
        }

        for (String field : updates.keySet()) {

            if (!field.equals("name") && !field.equals("email")) {
                throw new IllegalArgumentException(
                        "Unsupported field: " + field
                );
            }
        }

        if (updates.containsKey("name")) {
            Object name = updates.get("name");

            if (!(name instanceof String) || ((String) name).isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }

            if (((String) name).length() < 2 || ((String) name).length() > 50) {
                throw new IllegalArgumentException(
                        "Name must be between 2 and 50 characters"
                );
            }

            user.setName((String) name);
        }

        if (updates.containsKey("email")) {
            Object email = updates.get("email");

            if (!(email instanceof String) ||
                    !((String) email).matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("Invalid email address");
            }

            user.setEmail((String) email);
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

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "name", "email");

    public Page<UserResponse> getUsersPaginated(int page, int size, String sortBy, String direction) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field");
        }

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else if (direction.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            throw new IllegalArgumentException("Invalid sort direction");
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ));
    }

}