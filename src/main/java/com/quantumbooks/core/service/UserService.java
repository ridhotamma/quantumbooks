package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.ChangePasswordRequest;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.dto.UserDto;
import com.quantumbooks.core.entity.User;
import com.quantumbooks.core.entity.UserRole;
import com.quantumbooks.core.exception.DuplicateResourceException;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.UserMapper;
import com.quantumbooks.core.repository.UserRepository;
import com.quantumbooks.core.specification.UserSpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public PaginatedResponseDto<UserDto> getUsers(int page, int size, String search, UserRole role, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<User> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(UserSpecification.searchByUsernameOrEmail(search));
        }
        if (role != null) {
            spec = spec.and(UserSpecification.hasRole(role));
        }

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserDto> users = userPage.getContent().stream()
                .map(userMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(users, userPage);
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(UserRole.ROLE_EMPLOYEE);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!existingUser.getUsername().equals(userDto.getUsername()) &&
                userRepository.existsByUsername(userDto.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (!existingUser.getEmail().equals(userDto.getEmail()) &&
                userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User updatedUser = userMapper.toEntity(userDto);
        updatedUser.setId(existingUser.getId());
        updatedUser.setPassword(existingUser.getPassword());

        if (!canChangeRole(existingUser.getRole(), updatedUser.getRole())) {
            updatedUser.setRole(existingUser.getRole());
        }

        User savedUser = userRepository.save(updatedUser);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new IllegalArgumentException("New passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private boolean canChangeRole(UserRole currentRole, UserRole newRole) {
        return currentRole == UserRole.ROLE_ADMIN;
    }
}