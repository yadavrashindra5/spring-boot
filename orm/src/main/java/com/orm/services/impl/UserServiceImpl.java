package com.orm.services.impl;

import com.orm.dtos.UserDto;
import com.orm.entities.User;
import com.orm.repositories.UserRepository;
import com.orm.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        userDto.setId(UUID.randomUUID().toString());
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto get(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        return modelMapper.map(user, UserDto.class);
    }
}
