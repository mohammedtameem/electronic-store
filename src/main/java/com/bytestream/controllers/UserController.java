package com.bytestream.controllers;

import com.bytestream.dtos.APIResponseMessage;
import com.bytestream.dtos.UserDto;
import com.bytestream.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
      UserDto response =  userService.createUser(userDto);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
                                              @RequestBody UserDto userDto) {
        UserDto response =  userService.updateUser(userDto,userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponseMessage> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        APIResponseMessage message = APIResponseMessage.builder()
                .message("User is deleted successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
//        userService.deleteUser(userId);
//        return new ResponseEntity<>("User is delete succesfully", HttpStatus.OK);

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailId) {
        return new ResponseEntity<>(userService.getUserByEmail(emailId),HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keywords) {
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }


}
