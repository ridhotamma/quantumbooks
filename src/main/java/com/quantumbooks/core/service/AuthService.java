package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.*;
import com.quantumbooks.core.entity.User;
import com.quantumbooks.core.entity.UserRole;
import com.quantumbooks.core.exception.DuplicateResourceException;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.repository.UserRepository;
import com.quantumbooks.core.security.JwtTokenProvider;
import com.quantumbooks.core.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userPrincipal.getId() + " is not found"));

        user.setLastLoginDate(LocalDateTime.now());
        userRepository.save(user);

        String jwt = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(tokenProvider.getUserIdFromJWT(jwt));
        return new JwtAuthenticationResponse(jwt, refreshToken);
    }

    @Transactional
    public User registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFullName(signUpRequest.getFullName());

        UserRole userRole = UserRole.ROLE_EMPLOYEE;
        user.setRole(userRole);

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        boolean isRefreshTokenValid = tokenProvider.validateToken(refreshTokenRequest.getRefreshToken());
        if (!isRefreshTokenValid) {
            throw new RuntimeException("Refresh token is invalid");
        }

        Long userId = tokenProvider.getUserIdFromJWT(refreshTokenRequest.getRefreshToken());
        String newAccessToken = tokenProvider.generateToken(userId);
        String newRefreshToken = tokenProvider.generateRefreshToken(userId);

        return new JwtAuthenticationResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void resetPasswordByAdmin(Long userId, ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void resetPasswordByUser(Long userId, ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));

        if (!passwordEncoder.matches(resetPasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}