package com.bloggingapp.service.impl;

import com.bloggingapp.entities.User;
import com.bloggingapp.exceptions.ResourceNotFoundException;
import com.bloggingapp.payloads.UserDto;
import com.bloggingapp.repositories.UserRepository;
import com.bloggingapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repositories;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Converting to user because table is of User type , Not the UserDto type
        User user = this.dtoToUser(userDto);
        User savedUser = this.repositories.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // Here UserDto object will be given as a json object
        User user = this.repositories.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        // Again save the updated user
        User updatedUser = this.repositories.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.repositories.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.repositories.findAll();
        // Converting All Users to UserDto
        // use of stream API and Map method
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.repositories.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        this.repositories.delete(user);
    }


    // Converting DTO to User and User to DTO object
    public User dtoToUser(UserDto userDto){
        // With the help of model mapper we can convert UserDto to User
        User user = this.mapper.map(userDto, User.class);
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto (User user){
        UserDto userDto = this.mapper.map(user, UserDto.class);
        return userDto;
    }
}
