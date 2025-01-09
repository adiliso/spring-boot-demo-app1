package az.edu.turing.springbootdemoapp1.domain.repository.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Set<UserEntity> USERS = new HashSet<>();

    @Override
    public UserEntity save(UserEntity userEntity) {
        return null;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return USERS
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<UserEntity> findAll() {
        return USERS;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return USERS.stream().anyMatch(u -> u.getId().equals(id));
    }

    @Override
    public void deleteById(Long id) {

    }
}
