package com.bytestream.services;

import com.bytestream.config.UserMapper;
import com.bytestream.dtos.UserDto;
import com.bytestream.entities.User;
import com.bytestream.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

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
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User with "+  userId + "not found"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        user.setAbout(userDto.getAbout());

        User updateUser = userRepository.save(user);
        return entityToDTO(updateUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User with "+  userId + "not found"));
        userRepository.delete(user);

        //userRepository.deleteById(userId);

    }

    @Override
    public List<UserDto> getAllUser() {
       return userRepository.findAll().stream().
                 map(user -> entityToDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
      User user =  userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email Not Found"));
      return entityToDTO(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
       List<User> users = userRepository.findByNameContaining(keyword);
       return users.stream().map(user -> entityToDTO(user)).collect(Collectors.toList());

    }

//     private UserDto entityToDTO(User user) {
// //        UserDto userDto = UserDto.builder()
// //                .userId(user.getUserId())
// //                .name(user.getName())
// //                .email(user.getEmail())
// //                .gender(user.getGender())
// //                .password(user.getPassword())
// //                .about(user.getAbout())
// //                .imageName(user.getImageName())
// //                .build();
// //        return userDto;

//         return mapper.map(user, UserDto.class);

//     }

//     private User dtoToEntity(UserDto userDto) {
// //        User user = User.builder()
// //                .userId(userDto.getUserId())
// //                .name(userDto.getName())
// //                .email(userDto.getEmail())
// //                .gender(userDto.getGender())
// //                .password(userDto.getPassword())
// //                .about(userDto.getAbout())
// //                .imageName(userDto.getImageName())
// //                .build();
// //        return user;

//         return mapper.map(userDto, User.class);

//     }

 private UserDto entityToDTO(User user) {
        return userMapper.entityToDTO(user);
    }

    private User dtoToEntity(UserDto userDto) {
        return userMapper.dtoToEntity(userDto);
    }
}

