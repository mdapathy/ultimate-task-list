package com.duminska.ultimatetasklist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    void getAllTasksByProject(String projectId) {
        //TODO
    }

    void deleteTaskById() {
        //TODO
    }

    void editTaskById() {
        //TODO
    }

    Task addTask() {
        //TODO
        return null;
    }

    void markTaskAsDoneById() {
        //TODO
    }

    Task getTaskById(String taskId) {
        return null; //TODO
    }

}
