package az.edu.turing.springbootdemoapp1.domain.repository.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Set<UserEntity> USERS = new HashSet<>();
    private static final AtomicLong counter = new AtomicLong();

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            userEntity.setId(counter.incrementAndGet());
        } else {
            Long id = userEntity.getId();
            UserEntity userEntity1 = findById(id)
                    .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
            userEntity1.setUsername(userEntity.getUsername());
            userEntity1.setPassword(userEntity.getPassword());
            userEntity = userEntity1;
            deleteById(id);
        }
        USERS.add(userEntity);
        return userEntity;
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
        return USERS.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<UserEntity> findAll() {
        return USERS;
    }

    @Override
    public boolean existsByUsername(String username) {
        return USERS.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public boolean existsById(Long id) {
        return USERS.stream().anyMatch(u -> u.getId().equals(id));
    }

    @Override
    public Optional<UserEntity> deleteById(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }

        Optional<UserEntity> userEntity = findById(id);
        USERS.removeIf(u -> u.getId().equals(id));

        return userEntity;
    }
}
