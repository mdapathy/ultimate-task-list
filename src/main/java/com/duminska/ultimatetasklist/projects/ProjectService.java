package com.duminska.ultimatetasklist.projects;

import com.duminska.ultimatetasklist.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    final private ProjectDao projectDao;

    @Autowired
    public ProjectService(ProjectDao projectDao
    ) {
        this.projectDao = projectDao;
    }

    public String initProjectsForNewUser(String userId) {
        return projectDao.initProjectsForNewUser(userId);
    }

    DtoProject addProject(DtoProject dtoProject, String userId) {
        if (!dtoProject.getUserId().equals(userId)) {
            throw new ValidationException("Project assigned to another user");
        }

        return DtoProject.fromProject(projectDao.addProject(DtoProject.toProject(dtoProject)));
    }

    void deleteProject(String projectId, String userId) {
        Project project = projectDao.getProjectById(projectId);

        if (project == null) {
            throw new ValidationException("No project with such id");
        } else if (!project.getUserId().equals(userId)) {
            throw new ValidationException("Project belongs to another user");
        }

        projectDao.deleteProject(projectId);
    }

    void editProject(DtoProject project, String userId) {
        Project existingProject = projectDao.getProjectById(project.getProjectId());

        if (existingProject == null) {
            throw new ValidationException("No project with such id");
        } else if (!existingProject.getUserId().equals(userId) || !project.getUserId().equals(existingProject.getUserId())) {
            throw new ValidationException("Project belongs to another user");
        }

        projectDao.editProject(DtoProject.toProject(project));

    }

    List<Project> getUserProjects(String userId) {
        return projectDao.getUserProjects(userId);
    }


    public Project getProjectById(String projectId, String userId) {
        Project project = projectDao.getProjectById(projectId);
        if (project == null) {
            throw new ValidationException("No project with such id");
        } else if (!project.getUserId().equals(userId)) {
            throw new ValidationException("Project belongs to another user");
        }

        return project;
    }

    List<Project> getFavouriteProjects(String userId) {
        return projectDao.getFavouriteProjects(userId);
    }

}
