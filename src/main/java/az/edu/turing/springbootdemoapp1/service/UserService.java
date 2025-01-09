package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.model.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    Collection<UserDto> getAllUsers();

    UserDto updateUser(Long id, UserDto userDto);

    UserDto deleteUser(Long id);

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);
}
