package springlesson.controller;

import springlesson.model.UserProfile;
import springlesson.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class UserProfileController {

    private final UserProfileService profileService;

    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public UserProfile createProfile(
            @PathVariable Long userId,
            @RequestBody UserProfile profile) {

        return profileService.createProfile(userId, profile);
    }

    @GetMapping
    public UserProfile getProfile(@PathVariable Long userId) {
        return profileService.getProfile(userId);
    }
}
