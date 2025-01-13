package az.edu.turing.springbootdemoapp1.domain.repository.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.mapper.UserRowMapper;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Repository("userJdbcRepoImpl")
@RequiredArgsConstructor
public class UserJdbcRepoImpl implements UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            String query = """
                    INSERT INTO users (username, password, status) VALUES (:username, :password, :status) RETURNING id;
                    """;
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", userEntity.getUsername());
            params.addValue("password", userEntity.getPassword());
            params.addValue("status", userEntity.getUserStatus().toString());

            Long id = namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
            userEntity.setId(id);
        } else {
            String query = """
                    UPDATE users SET username=:username, password=:password, status=:status where id=:id;
                    """;
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", userEntity.getId());
            params.addValue("username", userEntity.getUsername());
            params.addValue("password", userEntity.getPassword());
            params.addValue("status", userEntity.getUserStatus().toString());

            namedParameterJdbcTemplate.update(query, params);
        }
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        String query = """
                SELECT * FROM users WHERE username = :username;
                """;
        return Optional.ofNullable(namedParameterJdbcTemplate.query(
                query,
                Collections.singletonMap("username", username),
                userRowMapper
        ).getFirst());
    }

    @Override
    public boolean existsByUsername(String username) {
        String query = """
                SELECT EXISTS (SELECT 1 FROM users WHERE username = :username);
                """;
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                query,
                Collections.singletonMap("username", username),
                Boolean.class)
        );
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        String query = """
                SELECT * FROM users WHERE id = :id;
                """;
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                query,
                Collections.singletonMap("id", id),
                userRowMapper
        ));
    }

    @Override
    public boolean existsById(Long id) {
        String query = """
                SELECT EXISTS (SELECT 1 FROM users WHERE username = :id);
                """;
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                query,
                Collections.singletonMap("id", id),
                Boolean.class)
        );
    }

    @Override
    public Collection<UserEntity> findAll() {
        String query = """
                SELECT * FROM users;
                """;
        return namedParameterJdbcTemplate.query(query, userRowMapper);
    }

    @Override
    public Optional<UserEntity> deleteById(Long id) {
        return updateStatus(id, UserStatus.DELETED);
    }

    @Override
    public Optional<UserEntity> updateStatus(Long id, UserStatus userStatus) {
        String query = """
                UPDATE users SET status = :status WHERE id = :id RETURNING *;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", userStatus.toString());
        params.addValue("id", id);

        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                query,
                params,
                userRowMapper));
    }
}
