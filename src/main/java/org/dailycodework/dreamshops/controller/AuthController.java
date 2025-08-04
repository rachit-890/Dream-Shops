package org.dailycodework.dreamshops.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.request.LoginRequest;
import org.dailycodework.dreamshops.response.ApiResponse;
import org.dailycodework.dreamshops.response.JwtResponse;
import org.dailycodework.dreamshops.security.jwt.JwtUtils;
import org.dailycodework.dreamshops.security.user.ShopUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
@Tag(name = "Authentication", description = "Operations about authentication")
public class AuthController  {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login")
    public ResponseEntity<ApiResponse> login( @Valid @RequestBody LoginRequest request) {

            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(),request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtUtils.generateTokenForUSer(authentication);
            ShopUserDetails userDetails=(ShopUserDetails) authentication.getPrincipal();

            JwtResponse jwtResponse=new JwtResponse(userDetails.getId(),jwt);
            return ResponseEntity.ok(new ApiResponse("Login Success",jwtResponse));
    }
}
