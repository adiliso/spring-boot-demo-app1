package az.edu.turing.springbootdemoapp1.domain.entity;

import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @ToString.Exclude
    private String password;

    @Column(name = "status")
    private UserStatus userStatus;
}
