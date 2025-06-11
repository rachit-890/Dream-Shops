package org.dailycodework.dreamshops.service.user;

import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.User;
import org.dailycodework.dreamshops.repository.UserRepository;
import org.dailycodework.dreamshops.request.CreateUserRequest;
import org.dailycodework.dreamshops.request.UserUpdateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,()->{throw new ResourceNotFoundException("User not found!");});

    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return null;
    }
}
