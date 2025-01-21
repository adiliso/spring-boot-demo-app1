package az.edu.turing.springbootdemoapp1.service.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.AlreadyExistsException;
import az.edu.turing.springbootdemoapp1.exception.InvalidInputException;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.mapper.UserMapper;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import az.edu.turing.springbootdemoapp1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(
            @Qualifier("userJdbcRepoImpl") UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(UserCreateRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InvalidInputException("Passwords don't match");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("User already exists with username: " + request.getUsername());
        }

        UserEntity savedUserEntity = userRepository.save(userMapper.toUserEntity(request));
        log.info("User created successfully with username: {}", request.getUsername());
        return userMapper.toUserDto(savedUserEntity);
    }

    @Override
    public Set<UserDto> getAll() {
        log.info("Getting all users...");
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto update(Long id, UserUpdateRequest request) {
        if (!userRepository.existsById(id)) throw new NotFoundException("User not found with id: " + id);
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("User already exists with username: " + request.getUsername());
        }
        UserEntity updatedUserEntity = userRepository.save(userMapper.toUserEntity(id, request));
        log.info("User updated successfully with id: {}", id);
        return userMapper.toUserDto(updatedUserEntity);
    }

    @Override
    public UserDto updateStatus(Long id, UserStatus userStatus) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));

        userEntity.setUserStatus(userStatus);
        return userMapper.toUserDto(userRepository.save(userEntity));
    }

    @Override
    public UserDto delete(Long id) {
        return updateStatus(id, UserStatus.DELETED);
    }

    @Override
    public UserDto getById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        log.info("User found successfully with id: {}", id);
        return userMapper.toUserDto(userEntity);
    }

    @Override
    public UserDto getByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
        log.info("User found successfully with username: {}", userEntity.getUsername());
        return userMapper.toUserDto(userEntity);
    }
}
