package az.edu.turing.springbootdemoapp1.service.impl;

import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto create(UserDto userDto) {
        return null;
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public UserDto deleteUser(Long id) {
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return null;
    }
}
