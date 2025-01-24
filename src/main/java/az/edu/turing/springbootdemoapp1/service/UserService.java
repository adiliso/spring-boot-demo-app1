package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional(readOnly = true)
public interface UserService {

    @Transactional
    UserDto create(UserCreateRequest userCreateRequest);

    Collection<UserDto> getAll();

    @Transactional
    UserDto update(Long id, UserUpdateRequest userUpdateRequest);

    @Transactional
    UserDto updateStatus(Long id, UserStatus userStatus);

    @Transactional
    UserDto delete(Long id);

    UserDto getById(Long id);

    UserDto getByUsername(String username);
}
