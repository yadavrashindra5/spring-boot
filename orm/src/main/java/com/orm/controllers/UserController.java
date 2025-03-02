package com.orm.controllers;

import com.orm.dtos.UserDto;
import com.orm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto userDto1 = userService.create(userDto);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto1, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> get(@PathVariable String userId) {
        UserDto postDto = userService.get(userId);
        ResponseEntity<UserDto> response = new ResponseEntity<>(postDto, HttpStatus.CREATED);
        return response;
    }
}
