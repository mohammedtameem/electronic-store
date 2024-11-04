package com.bytestream.services;

import com.bytestream.dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,String userId);

    void deleteUser(String userId);

    List<UserDto> getAllUser();

    UserDto getUserByEmail(String email);

    // search user with any keyword
    List<UserDto> searchUser(String keyword);

}
