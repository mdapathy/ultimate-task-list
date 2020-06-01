package com.duminska.ultimatetasklist.tasks;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {

        return Task.builder()
                .taskId(rs.getString("task_id"))
                .userId(rs.getString("user_id"))
                .name(rs.getString("name"))
                .color(rs.getString("color"))
                .priorityId(rs.getInt("priority_id"))
                .projectId(rs.getString("project_id"))
                .parentTaskId(rs.getString("parent_task_id"))
                .deadline(rs.getDate("first_deadline_date"))
                .recurring(rs.getDate("recurring_time"))
                .timesPostponed(rs.getInt("times_postponed"))
                .isDone(rs.getBoolean("is_done"))
                .build();
    }
}
