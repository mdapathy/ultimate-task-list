package com.duminska.ultimatetasklist.tasks;

import com.duminska.ultimatetasklist.config.constants.Constants;
import com.duminska.ultimatetasklist.config.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(Constants.TASK_URLS)
public class TaskController {

    final private TaskService taskService;
    final private AuthenticationFacade authenticationFacade;

    @Autowired
    public TaskController(TaskService taskService,
                          AuthenticationFacade authenticationFacade
    ) {
        this.taskService = taskService;
        this.authenticationFacade = authenticationFacade;

    }

    @PutMapping("/edit")
    public ResponseEntity<?> editTask(@RequestBody DtoTask dtoTask) {
        TaskValidator.validate(dtoTask);
        taskService.editTask(dtoTask, authenticationFacade.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addTask(@RequestBody DtoTask dtoTask) {
        TaskValidator.validate(dtoTask);
        return new ResponseEntity<>(taskService.addTask(dtoTask, authenticationFacade.getUserId()), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
        taskService.deleteTaskById(taskId, authenticationFacade.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{taskId}/mark-done")
    public ResponseEntity<?> markTaskAsDoneById(@PathVariable String taskId) {
        taskService.markTaskAsDoneById(taskId, authenticationFacade.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
