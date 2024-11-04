package com.bytestream.services;

import com.bytestream.dtos.UserDto;
import com.bytestream.entities.User;
import com.bytestream.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        // converting dto to entity object

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        // converting entity to DTO

        UserDto newDto = entityToDTO(savedUser);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserDto> getAllUser() {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        return null;
    }

    private UserDto entityToDTO(User user) {
        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .password(user.getPassword())
                .about(user.getAbout())
                .imageName(user.getImageName())
                .build();
        return userDto;

    }

    private User dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .imageName(userDto.getImageName())
                .build();
        return user;

    }
}
