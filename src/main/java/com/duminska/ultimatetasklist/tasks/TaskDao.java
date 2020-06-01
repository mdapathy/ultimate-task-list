package com.duminska.ultimatetasklist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void initTasksForNewUser(String userId) {
        //TODO
    }

    List<Task> getAllTasksByProject(String projectId) {
        return null; //TODO
    }

    void deleteTaskById(String taskId) {
        //TODO
    }

    void editTaskById(DtoTask task ) {
        //TODO
    }

    Task addTask(DtoTask task) {
        //TODO
        return null;
    }

    void markTaskAsDoneById(String taskId) {
        //TODO
    }

    Task getTaskById(String taskId) {
        return null; //TODO
    }

}
