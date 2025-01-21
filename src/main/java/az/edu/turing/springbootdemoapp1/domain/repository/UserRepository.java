package az.edu.turing.springbootdemoapp1.domain.repository;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<UserEntity> findById(Long id);

    boolean existsById(Long id);

    Collection<UserEntity> findAll();
}
