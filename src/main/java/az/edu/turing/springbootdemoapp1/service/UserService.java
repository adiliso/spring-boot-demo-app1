package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;

import java.util.Collection;

public interface UserService {

    UserDto create(UserCreateRequest userCreateRequest);

    Collection<UserDto> getAll();

    UserDto update(Long id, UserUpdateRequest userUpdateRequest);

    UserDto delete(Long id);

    UserDto getById(Long id);

    UserDto getByUsername(String username);
}
