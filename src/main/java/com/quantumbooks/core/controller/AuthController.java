package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.*;
import com.quantumbooks.core.entity.User;
import com.quantumbooks.core.security.UserPrincipal;
import com.quantumbooks.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User result = authService.registerUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/reset-password/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> resetPasswordByAdmin(@PathVariable Long userId, @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPasswordByAdmin(userId, resetPasswordRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
    }

    @PostMapping("/reset-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> resetPasswordByUser(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        authService.resetPasswordByUser(userId, resetPasswordRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
    }
}
