package com.duminska.ultimatetasklist.projects;

import com.duminska.ultimatetasklist.config.Constants;
import com.duminska.ultimatetasklist.config.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        System.out.println(authenticationFacade.getUserId());
        return new ResponseEntity(authenticationFacade.getUserId(), HttpStatus.OK);
    }

}
