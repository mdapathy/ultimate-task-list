package com.duminska.ultimatetasklist.user;

import com.duminska.ultimatetasklist.config.constants.SqlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

@Repository
public class UserDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    SqlConstants.USER_GET_BY_EMAIL, new Object[]{email}, new UserMapper());
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return null;
        }
    }


    public User getByActivationLink(String link) {
        try {
            return jdbcTemplate.queryForObject(
                    SqlConstants.USER_GET_BY_ACTIVATION_LINK, new Object[]{link}, new UserMapper());
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return null;
        }
    }


    User signUp(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SqlConstants.USER_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getActivationLink());
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKeys()).get("user_id").toString());
        user.setAccCreationDate((Date) Objects.requireNonNull(keyHolder.getKeys()).get("acc_creation_date"));

        return user;
    }

    void activateUser(String userId) {
        jdbcTemplate.update(SqlConstants.USER_ACTIVATE_USER, userId);
    }


    void deleteUserById(String userId) {
        jdbcTemplate.update(SqlConstants.USER_DELETE_USER, userId);
    }


}
