package springlesson.service;

import springlesson.exception.ResourceNotFoundException;
import springlesson.model.User;
import springlesson.model.UserProfile;
import springlesson.repository.UserProfileRepository;
import springlesson.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;

    public UserProfileService(
            UserProfileRepository profileRepository,
            UserRepository userRepository) {

        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public UserProfile createProfile(Long userId, UserProfile profile) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID " + userId + " not found"
                        )
                );

        if (profileRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException(
                    "User already has a profile"
            );
        }

        profile.setUser(user);

        return profileRepository.save(profile);
    }

    public UserProfile getProfile(Long userId) {

        return profileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Profile for user ID " + userId + " not found"
                        )
                );
    }
}
