package az.edu.turing.springbootdemoapp1.common;

import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;

public interface TestConstants {
    Long ID = 1L;
    String USERNAME = "test@gmail.com";
    String PASSWORD = "Test@0101";
    UserStatus USER_STATUS = UserStatus.ACTIVE;

    UserDto USER_DTO = UserDto.builder()
            .id(ID)
            .username(USERNAME)
            .password(PASSWORD)
            .userStatus(USER_STATUS)
            .build();

    UserCreateRequest USER_CREATE_REQUEST = UserCreateRequest.builder()
            .username(USERNAME)
            .password(PASSWORD)
            .confirmPassword(PASSWORD)
            .build();

    UserUpdateRequest USER_UPDATE_REQUEST = UserUpdateRequest.builder()
            .username(USERNAME)
            .password(PASSWORD)
            .userStatus(USER_STATUS)
            .build();
}
