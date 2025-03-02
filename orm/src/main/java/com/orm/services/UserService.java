package com.orm.services;

import com.orm.dtos.UserDto;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto get(String userId);
}
