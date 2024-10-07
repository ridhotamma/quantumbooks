package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.UserDto;
import com.quantumbooks.core.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
