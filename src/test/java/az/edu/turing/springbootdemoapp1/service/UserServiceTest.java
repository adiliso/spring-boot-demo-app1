package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.AlreadyExistsException;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.mapper.UserMapper;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
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
        given(userRepository.findAll()).willReturn(Set.of(USER_ENTITY));

        Set<UserDto> users = userService.getAll();
        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(Set.of(USER_DTO), users);

        then(userRepository).should(times(1)).findAll();
    }

    @Test
    void getByUsername_Should_Return_Success() {
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(USER_ENTITY));

        UserDto userDto = userService.getByUsername(USERNAME);
        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);

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
        given(userRepository.findById(ID)).willReturn(Optional.of(USER_ENTITY));

        UserDto userDto = userService.getById(ID);
        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);

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
        given(userRepository.save(USER_CREATE_ENTITY)).willReturn(USER_ENTITY);

        UserDto userDto = userService.create(USER_CREATE_REQUEST);

        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);

        then(userRepository).should(times(1)).save(USER_CREATE_ENTITY);
    }

    @Test
    void create_Should_ThrowAlreadyExistsException_When_UserAlreadyExists() {
        given(userRepository.existsByUsername(USERNAME)).willReturn(true);

        AlreadyExistsException ex = Assertions.assertThrows(AlreadyExistsException.class,
                () -> userService.create(USER_CREATE_REQUEST));

        assertEquals("User already exists with username: " + USERNAME, ex.getMessage());

        then(userRepository).should(times(1)).existsByUsername(USERNAME);
    }

    @Test
    void update_Should_Return_Success() {
        given(userRepository.save(USER_ENTITY)).willReturn(USER_ENTITY);

        UserDto userDto = userService.update(ID, UserUpdateRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .userStatus(UserStatus.ACTIVE)
                .build());
        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);

        then(userRepository).should(times(1)).save(USER_ENTITY);
    }

    @Test
    void update_Should_ThrowAlreadyExistsException_When_UserAlreadyExists() {
        given(userRepository.existsByUsername(USERNAME)).willReturn(true);

        AlreadyExistsException ex = Assertions.
                assertThrows(AlreadyExistsException.class,
                        () -> userService.update(ID, USER_UPDATE_REQUEST));
        assertEquals("User already exists with username: " + USERNAME, ex.getMessage());

        then(userRepository).should(times(1)).existsByUsername(USERNAME);
    }

    @Test
    void updateStatus_Should_Return_Success() {
        given(userRepository.updateStatus(ID, UserStatus.ACTIVE)).
                willReturn(Optional.of(USER_ENTITY));

        UserDto userDto = userService.updateStatus(ID, UserStatus.ACTIVE);
        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);

        then(userRepository).should(times(1))
                .updateStatus(ID, UserStatus.ACTIVE);
    }

    @Test
    void updateStatus_Should_ThrowNotFoundException_When_IdNotFound() {
        given(userRepository.updateStatus(ID, UserStatus.ACTIVE)).willReturn(Optional.empty());

        NotFoundException ex = Assertions.
                assertThrows(NotFoundException.class,
                        () -> userService.updateStatus(ID, UserStatus.ACTIVE));

        assertEquals("User not found with id: 1", ex.getMessage());

        then(userRepository).should(times(1))
                .updateStatus(ID, UserStatus.ACTIVE);
    }

    @Test
    void delete_Should_Return_Success() {
        given(userRepository.deleteById(ID)).willReturn(Optional.of(USER_ENTITY));

        UserDto userDto = userService.delete(ID);
        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);

        then(userRepository).should(times(1)).deleteById(ID);
    }

    @Test
    void delete_Should_ThrowNotFoundException_When_IdNotFound() {
        given(userRepository.deleteById(ID)).willReturn(Optional.empty());

        NotFoundException ex = Assertions.
                assertThrows(NotFoundException.class, () -> userService.delete(ID));

        assertEquals("User not found with id: " + ID, ex.getMessage());

        then(userRepository).should(times(1)).deleteById(ID);
    }
}
