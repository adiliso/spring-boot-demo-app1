package az.edu.turing.springbootdemoapp1.domain.repository.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Repository("userRepositoryImpl")
public class UserRepositoryImpl implements UserRepository {

    private static final Set<UserEntity> USERS = new HashSet<>();
    private static final AtomicLong counter = new AtomicLong();

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            userEntity.setId(counter.incrementAndGet());

        } else {
            USERS.remove(userEntity);
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
        return new HashSet<>(USERS);
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
        return updateStatus(id, UserStatus.DELETED);
    }

    @Override
    public Optional<UserEntity> updateStatus(Long id, UserStatus userStatus) {
        Optional<UserEntity> userEntity = findById(id);
        if (userEntity.isPresent()) {
            UserEntity userEntityToUpdate = userEntity.get();
            userEntityToUpdate.setUserStatus(userStatus);
            USERS.add(userEntityToUpdate);
            return Optional.of(userEntityToUpdate);
        }
        return Optional.empty();
    }
}
