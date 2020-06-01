package com.duminska.ultimatetasklist.tasks;

import com.duminska.ultimatetasklist.exception.ValidationException;
import com.duminska.ultimatetasklist.projects.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    void initTasksForNewUser(String userId) {
        taskDao.initTasksForNewUser(userId);
    }


    void getAllTasksByProject(String projectId, String userId) {
        if (projectService.getProjectById(projectId, userId) == null) {
            throw new ValidationException("No such project");
        }
        taskDao.getAllTasksByProject(projectId);
    }

    void deleteTaskById(String projectId, String userId) {

    }

    void editTaskById() {
        //TODO
    }

    void addTaskById() {
        //TODO
    }

    void markTaskAsDoneById() {
        //TODO
    }

    Task getTaskById(String taskId) {
        return taskDao.getTaskById(taskId);
    }

}
