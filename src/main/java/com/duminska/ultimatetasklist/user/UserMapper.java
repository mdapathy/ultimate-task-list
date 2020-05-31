package com.duminska.ultimatetasklist.user;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        return User.builder()
                .id(resultSet.getString("user_id"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .accCreationDate(resultSet.getDate("acc_creation_date"))
                .activationLink(resultSet.getString("activation_link"))
                .isActivated(resultSet.getBoolean("is_activated"))
                .recoveryLink(resultSet.getString("recovery_link"))
                .recoverySentDate(resultSet.getDate("recovery_sent_date"))
                .build();
    }
}
