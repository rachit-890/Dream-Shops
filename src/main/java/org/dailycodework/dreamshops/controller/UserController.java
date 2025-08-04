package org.dailycodework.dreamshops.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag( name = "Users", description = "Operations about users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    @Operation(summary = "Get user by id", description = "Get user by id")
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
    @Operation(summary = "Create user", description = "Create user")
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
    @Operation(summary = "Update user by id", description = "Update user by id")
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
    @Operation(summary = "Delete user by id", description = "Delete user by id")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!",null));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
