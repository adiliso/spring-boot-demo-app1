package az.edu.turing.springbootdemoapp1.model.dto;

import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private UserStatus userStatus;
}

