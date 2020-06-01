package com.duminska.ultimatetasklist.tasks;

import com.duminska.ultimatetasklist.exception.ValidationException;
import com.duminska.ultimatetasklist.projects.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskDao taskDao;
    private final ProjectService projectService;

    @Autowired
    public TaskService(TaskDao taskDao,
                       ProjectService projectService
    ) {
        this.projectService = projectService;
        this.taskDao = taskDao;
    }

    public void initTasksForNewUser(String userId) {
        taskDao.initTasksForNewUser(userId);
    }


    List<Task> getAllTasksByProject(String projectId, String userId) {
        if (projectService.getProjectById(projectId, userId) == null) {
            throw new ValidationException("No such project");
        }
        return taskDao.getAllTasksByProject(projectId);
    }

    void deleteTaskById(String taskId, String userId) {
        if (!getTaskById(taskId).getUserId().equals(userId)) {
            throw new ValidationException("Task belongs to another user");

        }
        taskDao.deleteTaskById(taskId);
    }

    void editTask() {
        //TODO
    }

    void addTask() {
        //TODO
    }

    void markTaskAsDoneById(String taskId, String userId) {
        if (!getTaskById(taskId).getUserId().equals(userId)) {
            throw new ValidationException("Task belongs to another user");

        }
        taskDao.markTaskAsDoneById(taskId);
    }

    Task getTaskById(String taskId) {
        return taskDao.getTaskById(taskId);
    }

}
