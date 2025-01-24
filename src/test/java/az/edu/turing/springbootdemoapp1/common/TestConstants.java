package az.edu.turing.springbootdemoapp1.common;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;

public final class TestConstants {

    public static final Long ID = 1L;
    public static final String USERNAME = "test@gmail.com";
    public static final String PASSWORD = "Test@0101";
    public static final UserStatus USER_STATUS = UserStatus.ACTIVE;

    public static UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(ID)
                .username(USERNAME)
                .password(PASSWORD)
                .userStatus(USER_STATUS)
                .build();
    }

    public static UserDto getUserDto() {
        return UserDto.builder()
                .id(ID)
                .username(USERNAME)
                .password(PASSWORD)
                .userStatus(USER_STATUS)
                .build();
    }

    public static UserCreateRequest getUserCreateRequest() {
        return UserCreateRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .confirmPassword(PASSWORD)
                .build();
    }

    public static UserUpdateRequest getUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .userStatus(USER_STATUS)
                .build();
    }
}