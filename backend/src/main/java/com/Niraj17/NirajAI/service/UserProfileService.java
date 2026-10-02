package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.profile.ProfileResponse;
import com.Niraj17.NirajAI.dto.profile.ProfileUpdateRequest;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.entity.UserProfile;
import com.Niraj17.NirajAI.repository.UserProfileRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProfileResponse getProfile(UUID userId) {
        UserProfile profile = getOrCreate(userId);
        return toResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(UUID userId, ProfileUpdateRequest request) {
        UserProfile profile = getOrCreate(userId);
        profile.update(
                request.displayName() != null ? request.displayName() : profile.getDisplayName(),
                request.bio() != null ? request.bio() : profile.getBio(),
                request.profileImageUrl() != null ? request.profileImageUrl() : profile.getProfileImageUrl(),
                request.preferredLanguage() != null ? request.preferredLanguage() : profile.getPreferredLanguage(),
                request.experienceLevel() != null ? request.experienceLevel() : profile.getExperienceLevel(),
                request.learningGoal() != null ? request.learningGoal() : profile.getLearningGoal());
        return toResponse(profileRepository.save(profile));
    }

    private UserProfile getOrCreate(UUID userId) {
        return profileRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            return profileRepository.save(new UserProfile(user));
        });
    }

    private ProfileResponse toResponse(UserProfile profile) {
        return new ProfileResponse(
                profile.getUser().getId(),
                profile.getDisplayName(),
                profile.getBio(),
                profile.getProfileImageUrl(),
                profile.getPreferredLanguage(),
                profile.getExperienceLevel(),
                profile.getLearningGoal());
    }
}
