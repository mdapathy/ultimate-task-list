package com.duminska.ultimatetasklist.tasks;

import com.duminska.ultimatetasklist.config.constants.SqlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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
                            SqlConstants.TASK_INIT_TASKS, Statement.RETURN_GENERATED_KEYS);
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
                SqlConstants.TASK_EDIT_TASK, task.getTaskId());
    }

    Task addTask(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            SqlConstants.TASK_ADD_TASK, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getUserId());
            ps.setString(2, task.getName());
            ps.setInt(3, task.getPriorityId());
            ps.setString(4, task.getProjectId());
            ps.setString(5, task.getParentTaskId());
            if(task.getDeadline() == null) {
                ps.setNull(6, Types.NULL);
            } else {
                ps.setTimestamp(6, new Timestamp(task.getDeadline().getTime()));

            }
            if(task.getRecurring() == null) {
                ps.setNull(7, Types.NULL);
            } else {
                ps.setTimestamp(7, new Timestamp(task.getRecurring().getTime()));
            }
            ps.setInt(8, task.getTimesPostponed());
            ps.setBoolean(9, task.isDone());
            System.out.println(ps.toString());
            return ps;
        }, keyHolder);
        task.setTaskId(Objects.requireNonNull(keyHolder.getKeys()).get("task_id").toString());
        return task;
    }

    List<Task> getAllTasksByProject(String projectId) {
        return jdbcTemplate.query(
                SqlConstants.TASK_GET_TASKS_BY_PROJECT, new Object[]{projectId},
                new TaskMapper()
        );
    }


    public List<Task> getTasksByProjectCompleted(String projectId) {
        return jdbcTemplate.query(
                SqlConstants.TASK_GET_TASKS_BY_PROJECT_COMPLETED, new Object[]{projectId},
                new TaskMapper()
        );
    }



    public List<Task> getAllTasksByProjectUnCompleted(String projectId) {
        return jdbcTemplate.query(
                SqlConstants.TASK_GET_TASKS_BY_PROJECT_UNCOMPLETED, new Object[]{projectId},
                new TaskMapper()
        );
    }


    void deleteTaskById(String taskId) {
        jdbcTemplate.update(SqlConstants.TASK_DELETE_TASK, taskId);
    }

    void markTaskAsDoneById(String taskId) {
        jdbcTemplate.update(SqlConstants.TASK_MARK_DONE_TASK, taskId);
    }

    Task getTaskById(String taskId) {
        return jdbcTemplate.queryForObject(
                SqlConstants.TASK_GET_BY_ID, new Object[]{taskId}, new TaskMapper()
        );
    }

}
