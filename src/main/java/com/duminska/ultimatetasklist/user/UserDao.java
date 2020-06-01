package com.duminska.ultimatetasklist.user;

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
                    "SELECT user_id, email, password," +
                            "is_activated, recovery_link, acc_creation_date, " +
                            "activation_link, recovery_sent_date from users WHERE email=?", new Object[]{email}, new UserMapper());
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return null;
        }
    }


    public User getByActivationLink(String link) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT user_id, email, password," +
                            "is_activated, recovery_link, acc_creation_date, " +
                            "activation_link, recovery_sent_date from users WHERE activation_link=?", new Object[]{link}, new UserMapper());
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return null;
        }
    }


    User signUp(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO users (email, password, activation_link) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.update("UPDATE users SET is_activated = true where user_id = uuid(?)", userId);
    }


    void deleteUserById(String userId) {
        jdbcTemplate.update("DELETE from users where user_id = uuid(?)", userId);
    }


}
