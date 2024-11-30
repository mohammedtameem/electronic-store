package com.bytestream.config;

import com.bytestream.dtos.UserDto;
import com.bytestream.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDTO (User user); // entity to DTO

    User dtoToEntity(UserDto userDto);

}
