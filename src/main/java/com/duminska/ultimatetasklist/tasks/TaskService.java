package com.duminska.ultimatetasklist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao
    ) {
        this.taskDao = taskDao;
    }

    void initTasksForNewUser(String userId) {
        taskDao.initTasksForNewUser(userId);
    }


}
