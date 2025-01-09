package az.edu.turing.springbootdemoapp1.domain.repository.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.AlreadyExistsException;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Set<UserEntity> USERS = new HashSet<>();

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (existsByUsername(userEntity.getUsername())) {
            throw new AlreadyExistsException("User already exists with username: " + userEntity.getUsername());
        }
        if (existsById(userEntity.getId())) {
            deleteById(userEntity.getId());
        }

        USERS.add(userEntity);
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return USERS.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public boolean existsByUsername(String username) {
        return USERS.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }

        USERS.removeIf(u -> u.getId().equals(id));
    }
}
