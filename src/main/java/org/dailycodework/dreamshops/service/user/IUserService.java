package org.dailycodework.dreamshops.service.user;


import org.dailycodework.dreamshops.dto.UserDto;
import org.dailycodework.dreamshops.model.User;
import org.dailycodework.dreamshops.request.CreateUserRequest;
import org.dailycodework.dreamshops.request.UserUpdateRequest;


public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    void deleteUser(Long userId);
    User updateUser(UserUpdateRequest request, Long userId);


    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
