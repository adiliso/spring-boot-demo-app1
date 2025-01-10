package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;

import java.util.Collection;

public interface UserService {

    UserDto create(UserCreateRequest userCreateRequest);

    Collection<UserDto> getAllUsers();

    UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest);

    UserDto deleteUser(Long id);

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);
}
