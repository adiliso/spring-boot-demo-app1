package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.AlreadyExistsException;
import az.edu.turing.springbootdemoapp1.exception.InvalidInputException;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.mapper.UserMapper;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import az.edu.turing.springbootdemoapp1.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static az.edu.turing.springbootdemoapp1.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAll_Should_Return_Success() {
        given(userRepository.findAll()).willReturn(Set.of(getUserEntity()));

        Set<UserDto> users = userService.getAll();
        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(Set.of(getUserDto()), users);

        then(userRepository).should(times(1)).findAll();
    }

    @Test
    void getByUsername_Should_Return_Success() {
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(getUserEntity()));

        UserDto userDto = userService.getByUsername(USERNAME);
        Assertions.assertNotNull(userDto);
        assertEquals(getUserDto(), userDto);

        then(userRepository).should(times(1)).findByUsername(USERNAME);
    }

    @Test
    void getByUsername_Should_ThrowNotFoundException_When_UsernameNotFound() {
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.empty());

        NotFoundException ex = Assertions.
                assertThrows(NotFoundException.class, () -> userService.getByUsername(USERNAME));

        assertEquals("User not found with username: " + USERNAME, ex.getMessage());

        then(userRepository).should(times(1)).findByUsername(USERNAME);
    }

    @Test
    void getById_Should_Return_Success() {
        given(userRepository.findById(ID)).willReturn(Optional.of(getUserEntity()));

        UserDto userDto = userService.getById(ID);
        Assertions.assertNotNull(userDto);
        assertEquals(getUserDto(), userDto);

        then(userRepository).should(times(1)).findById(ID);
    }

    @Test
    void getById_Should_ThrowNotFoundException_When_IdNotFound() {
        given(userRepository.findById(ID)).willReturn(Optional.empty());

        NotFoundException ex = Assertions.
                assertThrows(NotFoundException.class, () -> userService.getById(ID));

        assertEquals("User not found with id: 1", ex.getMessage());

        then(userRepository).should(times(1)).findById(ID);
    }

    @Test
    void create_Should_Return_Success() {
        var userEntity = getUserEntity();
        userEntity.setId(null);

        given(userRepository.save(userEntity)).willReturn(getUserEntity());

        UserDto userDto = userService.create(getUserCreateRequest());

        Assertions.assertNotNull(userDto);
        assertEquals(getUserDto(), userDto);

        then(userRepository).should(times(1)).save(userEntity);
    }

    @Test
    void create_Should_ThrowAlreadyExistsException_When_UserAlreadyExists() {
        given(userRepository.existsByUsername(USERNAME)).willReturn(true);

        var userCreateRequest = getUserCreateRequest();
        AlreadyExistsException ex = Assertions.assertThrows(AlreadyExistsException.class,
                () -> userService.create(userCreateRequest));

        assertEquals("User already exists with username: " + USERNAME, ex.getMessage());

        then(userRepository).should(times(1)).existsByUsername(USERNAME);
    }

    @Test
    void create_Should_InvalidInputException_When_PasswordsDoNotMatch() {
        UserCreateRequest userCreateRequest = getUserCreateRequest();
        userCreateRequest.setPassword("wrong password");

        InvalidInputException ex = Assertions.assertThrows(InvalidInputException.class,
                () -> userService.create(userCreateRequest));

        assertEquals("Passwords don't match", ex.getMessage());
    }

    @Test
    void update_Should_Return_Success() {
        given(userRepository.existsById(ID)).willReturn(true);
        given(userRepository.save(getUserEntity())).willReturn(getUserEntity());

        UserDto userDto = userService.update(ID, getUserUpdateRequest());
        Assertions.assertNotNull(userDto);
        assertEquals(getUserDto(), userDto);

        then(userRepository).should(times(1)).save(getUserEntity());
    }

    @Test
    void update_Should_ThrowAlreadyExistsException_When_UserAlreadyExists() {
        given(userRepository.existsById(ID)).willReturn(true);
        given(userRepository.existsByUsername(USERNAME)).willReturn(true);

        var userUpdateRequest = getUserUpdateRequest();
        AlreadyExistsException ex = Assertions.
                assertThrows(AlreadyExistsException.class,
                        () -> userService.update(ID, userUpdateRequest));
        assertEquals("User already exists with username: " + USERNAME, ex.getMessage());

        then(userRepository).should(times(1)).existsByUsername(USERNAME);
    }

    @Test
    void updateStatus_Should_Return_Success() {
        given(userRepository.findById(ID)).willReturn(Optional.of(getUserEntity()));
        given(userRepository.save(getUserEntity())).willReturn(getUserEntity());

        UserDto userDto = userService.updateStatus(ID, UserStatus.ACTIVE);
        Assertions.assertNotNull(userDto);
        assertEquals(getUserDto(), userDto);

        then(userRepository).should(times(1)).save(getUserEntity());
    }

    @Test
    void updateStatus_Should_ThrowNotFoundException_When_IdNotFound() {
        given(userRepository.findById(ID)).willReturn(Optional.empty());

        NotFoundException ex = Assertions.
                assertThrows(NotFoundException.class,
                        () -> userService.updateStatus(ID, UserStatus.ACTIVE));

        assertEquals("User not found with id: " + ID, ex.getMessage());

        then(userRepository).should(times(1)).findById(ID);
    }
}
