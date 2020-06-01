package com.duminska.ultimatetasklist.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void initProjectsForNewUser(String userId) {
        jdbcTemplate.update(
                "insert into projects (user_id, name) " +
                        "values (?, 'Inbox')", userId);

    }

    Project addProject(Project project) {
        return null;    //TODO

    }

    void deleteProject(String projectId) {
        jdbcTemplate.update(
                "delete from projects where project_id=uuid(?)", projectId);

    }

    Project editProject(Project project) {
        return null;    //TODO

    }

    List<Project> getUserProjects(String userId) {
        try {
            return jdbcTemplate.query("select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where user_id=uuid(?) ", new Object[]{userId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }

    }

    Project getProjectById(String projectId) {
        try {
            return jdbcTemplate.queryForObject("select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where project_id=uuid(?) ", new Object[]{projectId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }
    }


    List<Project> getFavouriteProjects(String userId) {
        try {
            return jdbcTemplate.query("select p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite,  p.user_id, p.project_id, p.name, p.color, p.parent_project_id, p.favourite from projects p where user_id=uuid(?) and favourite=true", new Object[]{userId}, new ProjectMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }
    }
}
