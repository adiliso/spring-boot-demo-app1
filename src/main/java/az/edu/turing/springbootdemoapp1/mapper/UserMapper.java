package az.edu.turing.springbootdemoapp1.mapper;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .userStatus(userDto.getUserStatus())
                .build();
    }

    public UserEntity toUserEntity(UserCreateRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    public UserEntity toUserEntity(Long id, UserUpdateRequest request) {
        return UserEntity.builder()
                .id(id)
                .username(request.getUsername())
                .password(request.getPassword())
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    public UserDto toUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .userStatus(userEntity.getUserStatus())
                .build();
    }

}
