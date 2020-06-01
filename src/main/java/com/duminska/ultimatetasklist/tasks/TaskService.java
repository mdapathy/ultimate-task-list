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

    public void initTasksForNewUser(String userId, String projectId) {
        taskDao.initTasksForNewUser(userId, projectId);
    }


    public List<Task> getAllTasksByProject(String projectId, String userId) {
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

    void editTask(DtoTask dtoTask, String userId) {
        checkTask(dtoTask, userId);
        taskDao.editTaskById(DtoTask.toTask(dtoTask));
    }

    DtoTask addTask(DtoTask dtoTask, String userId) {
        checkTask(dtoTask, userId);
        Task taskAdd = DtoTask.toTask(dtoTask);
        return DtoTask.fromTask(taskDao.addTask(taskAdd));
    }

    void markTaskAsDoneById(String taskId, String userId) {
        if (!getTaskById(taskId).getUserId().equals(userId)) {
            throw new ValidationException("Task belongs to another user");

        }
        taskDao.markTaskAsDoneById(taskId);
    }

    private Task getTaskById(String taskId) {
        return taskDao.getTaskById(taskId);
    }

    private void checkTask(DtoTask dtoTask, String userId) {
        if (!dtoTask.getUserId().equals(userId)) {
            throw new ValidationException("Tried to assign task to another user");
        }
        if (projectService.getProjectById(dtoTask.getProjectId(), userId) == null) {
            throw new ValidationException("No such project");

        }
    }

}
