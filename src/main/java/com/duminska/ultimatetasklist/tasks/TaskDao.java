package com.duminska.ultimatetasklist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class TaskDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void initTasksForNewUser(String userId, String projectId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "insert into tasks (user_id, name, priority_id, project_id)\n" +
                                    "values (uuid(?), ?, 4, uuid(?));", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            ps.setString(2, "Complete this task");
            ps.setString(3, projectId);
            ps.executeUpdate();
            ps.setString(1, userId);
            ps.setString(2, "Delete this task");
            ps.setString(3, projectId);
            ps.executeUpdate();
            ps.setString(1, userId);
            ps.setString(2, "Create a new task");
            ps.setString(3, projectId);
            return ps;
        }, keyHolder);

    }

    void editTaskById(Task task) {
        jdbcTemplate.update(
                "update tasks set name = ?, priority_id = ?, " +
                        "project_id = ?, parent_task_id = ?, first_deadline_date = ?," +
                        "recurring_time = ?, times_postponed = ?" +
                        "where task_id=uuid(?)", task.getTaskId());
    }

    Task addTask(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "insert into tasks (user_id, name, priority_id, " +
                                    "project_id, parent_task_id, first_deadline_date," +
                                    " recurring_time, times_postponed, is_done) " +
                                    "values (uuid(?),?,uuid(?),uuid(?),uuid(?),?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTaskId());
            ps.setString(2, task.getName());
            ps.setString(3, task.getPriorityId());
            ps.setString(4, task.getProjectId());
            ps.setString(5, task.getParentTaskId());
            ps.setTimestamp(6, new Timestamp(task.getDeadline().getTime()));
            ps.setTimestamp(7, new Timestamp(task.getRecurring().getTime()));
            ps.setInt(8, task.getTimesPostponed());
            ps.setBoolean(9, task.isDone());
            return ps;
        }, keyHolder);
        task.setTaskId(Objects.requireNonNull(keyHolder.getKeys()).get("task_id").toString());
        return task;
    }

    List<Task> getAllTasksByProject(String projectId) {
        return jdbcTemplate.query(
                "select task_id, user_id, name, priority_id, " +
                        "project_id, parent_task_id, first_deadline_date," +
                        " recurring_time, times_postponed, is_done, " +
                        "priorities.color as color from tasks left join " +
                        "priorities on tasks.priority_id = priorities.value " +
                        "where project_id=uuid(?)", new Object[]{projectId},
                new TaskMapper()
        );
    }

    void deleteTaskById(String taskId) {
        jdbcTemplate.update("delete from tasks  where task_id=uuid(?)", taskId);
    }

    void markTaskAsDoneById(String taskId) {
        jdbcTemplate.update("UPDATE tasks set is_done = true where task_id=uuid(?)", taskId);
    }

    Task getTaskById(String taskId) {
        return jdbcTemplate.queryForObject(
                "select task_id, user_id, name, priority_id, project_id, parent_task_id, first_deadline_date, recurring_time, times_postponed, is_done, priorities.color as color from tasks left join priorities on tasks.priority_id = priorities.value where task_id=uuid(?)", new Object[]{taskId}, new TaskMapper()
        );
    }

}
