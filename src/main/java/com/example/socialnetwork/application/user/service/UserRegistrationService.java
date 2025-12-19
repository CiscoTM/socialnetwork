package com.example.socialnetwork.application.user.service;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.error.DuplicateEmailException;
import com.example.socialnetwork.domain.user.ports.UserRepository;

public class UserRegistrationService {

    private final UserRepository userRepo;

    public UserRegistrationService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User register(String id, String email, String displayName) {

        UserId userId = UserId.of(id);
        UserEmail userEmail = UserEmail.of(email);

        if (userRepo.existsByEmail(userEmail)) {
            throw new DuplicateEmailException(email);
        }

        User user = User.create(userId, userEmail, displayName);
        userRepo.save(user);

        return user;
    }
}


