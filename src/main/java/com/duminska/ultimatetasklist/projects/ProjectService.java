package com.duminska.ultimatetasklist.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectDao projectDao;

    @Autowired
    public ProjectService(ProjectDao projectDao
    ) {
        this.projectDao = projectDao;
    }

    public void initProjectsForNewUser(String userId) {
        projectDao.initProjectsForNewUser(userId);
    }

    Project addProject(DtoProject project) {
        return null;    //TODO
    }

    void deleteProject(String projectId) {
        projectDao.deleteProject(projectId);
    }

    Project editProject(String projectId) {
        return null;    //TODO

    }

    List<Project> getUserProjects(String userId) {
        return projectDao.getUserProjects(userId);
    }


    Project getProjectById(String projectId) {
        return projectDao.getProjectById(projectId);
    }

    List<Project> getFavouriteProjects(String userId) {
        return projectDao.getFavouriteProjects(userId);
    }

}
