package az.edu.turing.springbootdemoapp1.mapper;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserCreateRequest;
import az.edu.turing.springbootdemoapp1.model.dto.requests.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userStatus", constant = "ACTIVE")
    UserEntity toUserEntity(UserCreateRequest request);

    UserEntity toUserEntity(Long id, UserUpdateRequest request);

    UserDto toUserDto(UserEntity entity);
}
