package com.duminska.ultimatetasklist.projects;

import com.duminska.ultimatetasklist.config.Constants;
import com.duminska.ultimatetasklist.config.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(Constants.PROJECT_URLS)
public class ProjectController {

    final private ProjectService projectService;
    final private AuthenticationFacade authenticationFacade;

    @Autowired
    public ProjectController(ProjectService projectService,
                             AuthenticationFacade authenticationFacade) {
        this.projectService = projectService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProjects() {
        return new ResponseEntity<>(
                projectService.getUserProjects(authenticationFacade.getUserId())
                , HttpStatus.OK);
    }

    @GetMapping("/get-favourite")
    public ResponseEntity<?> getFavouriteProjects() {
        return new ResponseEntity<>(
                projectService.getFavouriteProjects(authenticationFacade.getUserId())
                , HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable("projectId") String id) {
        return new ResponseEntity<>(
                projectService.getProjectById(id, authenticationFacade.getUserId())
                , HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> addProject(DtoProject dtoProject) {
        return new ResponseEntity<>(
                projectService.addProject(dtoProject, authenticationFacade.getUserId())
                , HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<?> editProject(DtoProject dtoProject) {
        projectService.editProject(dtoProject, authenticationFacade.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") String id) {
        projectService.deleteProject(id, authenticationFacade.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
