package az.edu.turing.springbootdemoapp1.domain.repository.impl;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static az.edu.turing.springbootdemoapp1.model.constant.UserColumnNames.*;


@Repository("userJdbcRepoImpl")
@RequiredArgsConstructor
public class UserJdbcRepoImpl implements UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            String query = """
                    INSERT INTO users (username, password, status) VALUES (:username, :password, :status) RETURNING *;
                    """;
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(USERNAME, userEntity.getUsername());
            params.addValue(PASSWORD, userEntity.getPassword());
            params.addValue(STATUS, userEntity.getUserStatus().toString());

            return namedParameterJdbcTemplate.queryForObject(query, params, userRowMapper);
        } else {
            String query = """
                    UPDATE users SET username=:username, password=:password, status=:status where id=:id;
                    """;
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(ID, userEntity.getId());
            params.addValue(USERNAME, userEntity.getUsername());
            params.addValue(PASSWORD, userEntity.getPassword());
            params.addValue(STATUS, userEntity.getUserStatus().toString());

            namedParameterJdbcTemplate.update(query, params);
        }
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        String query = """
                SELECT * FROM users WHERE username = :username;
                """;
        return namedParameterJdbcTemplate.query(
                        query,
                        Collections.singletonMap(USERNAME, username),
                        userRowMapper)
                .stream()
                .findFirst();
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
        return namedParameterJdbcTemplate.query(
                        query,
                        Collections.singletonMap(ID, id),
                        userRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        String query = """
                SELECT EXISTS (SELECT 1 FROM users WHERE username = :id);
                """;
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                query,
                Collections.singletonMap(ID, id),
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
}
