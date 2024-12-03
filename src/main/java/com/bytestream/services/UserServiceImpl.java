package com.bytestream.services;

import com.bytestream.config.UserMapper;
import com.bytestream.dtos.UserDto;
import com.bytestream.entities.User;
import com.bytestream.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating a new user with details: {}", userDto);

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        logger.info("User created successfully with ID: {}", userId);
        return entityToDTO(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Updating user with ID: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    logger.error("User with ID: {} not found", userId);
                    return new RuntimeException("User with " + userId + " not found");
                });
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepository.save(user);

        logger.info("User with ID: {} updated successfully", userId);
        return entityToDTO(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        logger.warn("Deleting user with ID: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    logger.error("User with ID: {} not found", userId);
                    return new RuntimeException("User with " + userId + " not found");
                });
        userRepository.delete(user);

        logger.info("User with ID: {} deleted successfully", userId);
    }

    @Override
    public List<UserDto> getAllUser() {
        logger.info("Fetching all users");

        List<UserDto> userList = userRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        logger.info("Total users fetched: {}", userList.size());
        return userList;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Fetching user by email: {}", email);

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> {
                    logger.error("Email not found: {}", email);
                    return new RuntimeException("Email Not Found");
                });
        return entityToDTO(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Searching users with keyword: {}", keyword);

        List<User> users = userRepository.findByNameContaining(keyword);

        logger.info("Total users found with keyword '{}': {}", keyword, users.size());
        return users.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    private UserDto entityToDTO(User user) {
        return userMapper.entityToDTO(user);
    }

    private User dtoToEntity(UserDto userDto) {
        return userMapper.dtoToEntity(userDto);
    }
}
