package org.dailycodework.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.dto.UserDto;
import org.dailycodework.dreamshops.exception.AlreadyExitsException;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.User;
import org.dailycodework.dreamshops.request.CreateUserRequest;
import org.dailycodework.dreamshops.request.UserUpdateRequest;
import org.dailycodework.dreamshops.response.ApiResponse;
import org.dailycodework.dreamshops.service.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user=userService.getUserById(userId);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(new ApiResponse("Error",e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user=userService.createUser(request);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success!",userDto));
        } catch (AlreadyExitsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
        try {
            User user=userService.updateUser(request,userId);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!",null));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
